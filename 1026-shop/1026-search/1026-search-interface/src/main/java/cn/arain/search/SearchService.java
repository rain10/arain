package cn.arain.search;

import cn.arain.common.pojo.SearchResult;
import cn.arain.common.util.ArainResult;

public interface SearchService {
	ArainResult importData();
	public SearchResult search(String keyword, int page, int rows) throws Exception ;
	ArainResult itemAddSolr(Long id);
	
}
