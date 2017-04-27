package cn.arain.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Data;

import cn.arain.common.pojo.DataGrid;
import cn.arain.common.util.ArainResult;
import cn.arain.common.util.IDUtil;
import cn.arain.mapper.TbItemDescMapper;
import cn.arain.mapper.TbItemMapper;
import cn.arain.pojo.TbItem;
import cn.arain.pojo.TbItemDesc;
import cn.arain.pojo.TbItemExample;
import cn.arain.pojo.TbItemParam;
import cn.arain.service.ItemService;
/**
 * 
* <p>Title:ItemServiceImpl </p>
* @author Arain
* @date2017年4月7日
 */

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	@Override
	public TbItem load_Id(Long id) {
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		return item;
	}
	
	
	@Override
	public DataGrid load_list(int pageNum, int rowNum) {
		DataGrid dataGrid = new DataGrid();
		TbItemExample example = new TbItemExample();
		example.setOrderByClause("updated");
		
		PageHelper.startPage(pageNum, rowNum);
		
		List<TbItem> list = tbItemMapper.selectByExample(example);
		dataGrid.setRows(list);
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		dataGrid.setTotal(pageInfo.getTotal());
		
		return dataGrid;
	}


	@Override
	public ArainResult save_01(TbItem item,String desc) {
		//新增
		if(item.getId() == null) {
		final long id = IDUtil.genItemId();
		//保存商品
		item.setId(id);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		item.setStatus((byte) 1);
		tbItemMapper.insert(item);
		
		//保存描述
		if(!"".equals(desc)) {
			
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		
		tbItemDescMapper.insert(itemDesc);
		}
		
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(id+"");
				System.out.println(message);
				return message;
			}
		});
		
		return ArainResult.ok();
		} else {
			final Long id = item.getId();
			//编辑
			item.setUpdated(new Date());
			tbItemMapper.updateByPrimaryKey(item);
			if(!"".equals(desc)) {
			TbItemDesc itemDesc = this.load_desc(id);
			itemDesc.setItemDesc(desc);
			itemDesc.setUpdated(new Date());
			tbItemDescMapper.updateByPrimaryKey(itemDesc);
			}
			
			
			jmsTemplate.send(topicDestination, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = session.createTextMessage(id+"");
					return message;
				}
			});
			
			return ArainResult.ok();
		}
		
	}


	@Override
	public TbItem load_item(Long id) {
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
	
		return item;
	}


	@Override
	public TbItemDesc load_desc(Long id) {
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		return itemDesc;
	}


	@Override
	public TbItemParam load_parm(Long id) {
		
		return null;
	}


	@Override
	public ArainResult delete(Long[] ids) {
		for (Long id : ids) {
			tbItemMapper.deleteByPrimaryKey(id);
			tbItemDescMapper.deleteByPrimaryKey(id);
		}
		return ArainResult.ok();
	}
	

}
