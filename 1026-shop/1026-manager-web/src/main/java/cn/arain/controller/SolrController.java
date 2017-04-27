package cn.arain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arain.common.util.ArainResult;
import cn.arain.search.SearchService;

@Controller
public class SolrController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/item/import")
	@ResponseBody
	public ArainResult importData() {
		ArainResult result = searchService.importData();
		return result;
		
	}
}
