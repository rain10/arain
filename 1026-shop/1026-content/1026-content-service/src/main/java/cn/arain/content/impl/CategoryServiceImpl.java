package cn.arain.content.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.arain.common.pojo.DataGrid;
import cn.arain.common.pojo.DataTree;
import cn.arain.common.util.ArainResult;
import cn.arain.content.CategoryService;
import cn.arain.mapper.TbContentCategoryMapper;
import cn.arain.mapper.TbContentMapper;
import cn.arain.pojo.TbContent;
import cn.arain.pojo.TbContentCategory;
import cn.arain.pojo.TbContentCategoryExample;
import cn.arain.pojo.TbContentCategoryExample.Criteria;
import cn.arain.pojo.TbContentExample;
/**
 * 
* <p>Title:TbContentCategoryServiceImpl </p>
* @author Arain
* @date2017年4月19日
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Override
	public List<DataTree> load_tree(Long pid) {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(pid);
		List<DataTree> dataTrees = new  ArrayList<>();
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		
		for (TbContentCategory tbContentCategory : list) {
			DataTree tree = new DataTree();
			tree.setId(tbContentCategory.getId());
			tree.setText(tbContentCategory.getName());
			tree.setState(tbContentCategory.getIsParent() ? "closed":"open");
			
			dataTrees.add(tree);
		}
		return dataTrees;
	}

	@Override
	public DataGrid load_list(int pageNum, int rowNum, Long categoryId) {
		DataGrid grid = new DataGrid();
		
		TbContentExample example = new TbContentExample();
		
		cn.arain.pojo.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		PageHelper.startPage(pageNum, rowNum);
		
		List<TbContent> list = tbContentMapper.selectByExample(example);
		grid.setRows(list);
		
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		
		grid.setTotal(pageInfo.getTotal());
		
		return grid;
	}

	@Override
	public ArainResult save(TbContentCategory category) {
		if(category.getId() == null) {
			category.setStatus(1);
			category.setCreated(new Date());
			category.setUpdated(new Date());
			category.setIsParent(false);
			category.setSortOrder(1);
			
			tbContentCategoryMapper.insert(category);
			
			return ArainResult.ok();
		} else {
			TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(category.getId());
			contentCategory.setUpdated(new Date());
			contentCategory.setName(category.getName());

			tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
			return ArainResult.ok();
		}
		
	}

	@Override
	public ArainResult delete(TbContentCategory category) {
			tbContentCategoryMapper.deleteByPrimaryKey(category.getId());
			
		return ArainResult.ok();
	}

}
