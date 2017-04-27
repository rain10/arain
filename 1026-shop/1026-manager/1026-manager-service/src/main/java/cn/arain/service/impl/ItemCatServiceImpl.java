package cn.arain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.arain.common.pojo.DataTree;
import cn.arain.mapper.TbItemCatMapper;
import cn.arain.pojo.TbItemCat;
import cn.arain.pojo.TbItemCatExample;
import cn.arain.pojo.TbItemCatExample.Criteria;
import cn.arain.service.ItemCatService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	
	@Override
	public String load_tree(Long pid) {
		
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(pid);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		for (TbItemCat tbItemCat : list) {
			object.put("id", tbItemCat.getId());
			object.put("text", tbItemCat.getName());
			if(tbItemCat.getIsParent()) {
				object.put("states", "open");
			} else {
				object.put("states","close");
			}
			array.add(object);
		}
		
		
		
		return array.toString();
	}


	@Override
	public List<DataTree> load_tree_01(long pid) {
		//根据parentId查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(pid);
		//执行查询
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		//创建返回结果List
		List<DataTree> resultList = new ArrayList<>();
		//把列表转换成EasyUITreeNode列表
		for (TbItemCat tbItemCat : list) {
			DataTree node = new DataTree();
			//设置属性
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到结果列表
			resultList.add(node);
		}
		//返回结果
		return resultList;
	}

}
