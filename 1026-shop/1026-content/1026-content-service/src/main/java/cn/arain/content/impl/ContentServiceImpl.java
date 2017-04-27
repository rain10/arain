package cn.arain.content.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.arain.common.redis.JedisClient;
import cn.arain.common.util.ArainResult;
import cn.arain.common.util.JsonUtils;
import cn.arain.content.ContentService;
import cn.arain.mapper.TbContentMapper;
import cn.arain.pojo.TbContent;
import cn.arain.pojo.TbContentExample;
import cn.arain.pojo.TbContentExample.Criteria;
import net.sf.json.JSONArray;
/**
 * 
* <p>Title:ContentServiceImpl </p>
* @author Arain
* @date2017年4月20日
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_VALUE}")
	private String content_value;
	
	@Override
	public ArainResult save(TbContent content) {
		if(content.getId() == null) {
			content.setCreated(new Date());
			content.setUpdated(new Date());
			
			tbContentMapper.insert(content);
			
			try {
				jedisClient.hdel(content_value, content.getCategoryId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return ArainResult.ok();
		} else {
			
			
			try {
				jedisClient.hdel(content_value, content.getCategoryId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return ArainResult.ok();
		}
	}

	@Override
	public ArainResult edit(TbContent content) {
		TbContent tbContent = tbContentMapper.selectByPrimaryKey(content.getId());
		return ArainResult.ok(tbContent);
	}

	@Override
	public ArainResult delete(Long[] ids) {
		for (Long id : ids) {
			tbContentMapper.deleteByPrimaryKey(id);
		}
		return ArainResult.ok();
	}
	
	
	
	
	
	/**前台展示
	 */

	@Override
	public List<TbContent> load_ad(Long categoryId) {
		JSONArray array = new JSONArray();
		try {
			String hget = jedisClient.hget(content_value, categoryId.toString());
			if(StringUtils.isNotBlank(hget)) {
				List<TbContent> list = JsonUtils.jsonToList(hget, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> list = tbContentMapper.selectByExample(example);
		array.add(list);
		try {
			jedisClient.hset(content_value, categoryId.toString(),JsonUtils.objectToJson(list));
			jedisClient.expire(content_value, 60000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
