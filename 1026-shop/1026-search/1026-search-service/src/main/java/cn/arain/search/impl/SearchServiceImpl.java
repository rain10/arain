package cn.arain.search.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.arain.common.pojo.SearchItem;
import cn.arain.common.pojo.SearchResult;
import cn.arain.common.util.ArainResult;
import cn.arain.search.SearchDao;
import cn.arain.search.SearchService;
import cn.arain.search.mapper.SearchMapper;
/**
 * 
* <p>Title:SearchServiceImpl </p>
* @author Arain
* @date2017年4月21日
 */
@Service
public class SearchServiceImpl implements SearchService{
	@Autowired
	private SearchMapper searchMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchDao searchDao;
	
	
	@Override
	public ArainResult importData() {
//		SolrServer server = new HttpSolrServer("http://192.168.28.129:8080/solr/");
	
			try {
				List<SearchItem> list = searchMapper.importSearch();
				for (SearchItem searchItem : list) {
					//创建文档对象
					SolrInputDocument document = new SolrInputDocument();
					//向文档中添加域
					document.addField("id", searchItem.getId());
					document.addField("item_title", searchItem.getTitle());
					document.addField("item_sell_point", searchItem.getSell_point());
					document.addField("item_price", searchItem.getPrice());
					document.addField("item_image", searchItem.getImage());
					document.addField("item_category_name", searchItem.getCategory_name());
					//写入索引库
					
					solrServer.add(document);
						
					}
				solrServer.commit();
				return ArainResult.ok();
				
			} catch (Exception e) {
				e.printStackTrace();
				return ArainResult.build(500, "导入异常！");
			} 
	
	}
	
	
	
	@Override
	public ArainResult itemAddSolr(Long id) {
		try {
			SearchItem searchItem = searchMapper.getByItemId(id);
			
			//创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			//向文档中添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			//写入索引库
			
			solrServer.add(document);
			
			solrServer.commit();
			
			return  ArainResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ArainResult.build(500, "导入异常！");
		}
	}
	
	
	
	
	
	
	
	
	
	


	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		SolrQuery query = new SolrQuery();
		
		query.setQuery(keyword);
		
		if (page <=0 ) page =1;
		query.setStart((page - 1) * rows);


		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		//调用dao执行查询
		SearchResult searchResult = searchDao.search(query);
		//计算总页数
		long recordCount = searchResult.getRecordCount();
		int totalPage = (int) (recordCount / rows);
		if (recordCount % rows > 0) 
			totalPage ++;
		//添加到返回结果
		searchResult.setTotalPages(totalPage);
		//返回结果
		return searchResult;
	}


	

}
