package cn.arain.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * 
* <p>Title:SearchDao </p>
* @author Arain
* @date2017年4月24日
 */

import cn.arain.common.pojo.SearchItem;
import cn.arain.common.pojo.SearchResult;
@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws SolrServerException {
		//查询
		QueryResponse queryResponse = solrServer.query(query);
		//返回结果
		SolrDocumentList documentList = queryResponse.getResults();
		//记录数
		long numFound = documentList.getNumFound();
		//创建返回对象
		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		//查询高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : documentList) {
			SearchItem item = new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			
			
			//取得高亮显示列表
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			
			item.setTitle(title);
			
			itemList.add(item);
		}
		
		result.setItemList(itemList);
		return result;
		
	}
}
