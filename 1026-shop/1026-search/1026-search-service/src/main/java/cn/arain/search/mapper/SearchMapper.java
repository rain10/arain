package cn.arain.search.mapper;

import java.util.List;

import cn.arain.common.pojo.SearchItem;

public interface SearchMapper {
	
	List<SearchItem> importSearch();
	
	SearchItem getByItemId(Long id);
}
