package cn.arain.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arain.common.pojo.DataGrid;
import cn.arain.common.pojo.DataTree;
import cn.arain.common.util.ArainResult;
import cn.arain.content.CategoryService;
import cn.arain.pojo.TbContentCategory;
/**
 * 
* <p>Title:ContentCategoryController </p>
* @author Arain
* @date2017年4月19日
 */
@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<DataTree> load_category(@RequestParam(value="id",required=false,defaultValue="0")  Long pid) {
		List<DataTree> load_tree = categoryService.load_tree(pid);
		return load_tree;
	}
	
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public DataGrid load_list(@RequestParam(value="page",required=false) int pageNum,@RequestParam(value="rows",required=false) int rowNum,
			@RequestParam(value="categoryId",required=false) Long categoryId) {
		DataGrid dataGrid = categoryService.load_list(pageNum, rowNum, categoryId);
		
		return dataGrid;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public ArainResult add(TbContentCategory category) {
		ArainResult arainResult = categoryService.save(category);
		return arainResult;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public ArainResult update(TbContentCategory category) {
		ArainResult arainResult = categoryService.save(category);
		return arainResult;
	}
	
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public ArainResult delete(TbContentCategory category) {
		ArainResult result = categoryService.delete(category);
		return result;
		
	}
}
