package cn.arain.search;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.arain.common.pojo.SearchResult;

/**
 * 
 * <p>
 * Title:SearchController
 * </p>
 * 
 * @author Arain
 * @date2017年4月24日
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping("search.html")
	public String query(final HttpServletRequest request, Model model,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(defaultValue = "1") Integer page) throws Exception {
		keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");

		SearchResult result = searchService.search(keyword, page, 50);

		// 把结果传递给页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", result.getRecordCount());
		model.addAttribute("itemList", result.getItemList());

		return "search";

	}
}
