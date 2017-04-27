package cn.arain.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arain.common.pojo.DataGrid;
import cn.arain.common.pojo.DataTree;
import cn.arain.common.util.ArainResult;
import cn.arain.pojo.TbItem;
import cn.arain.pojo.TbItemDesc;
import cn.arain.service.ItemCatService;
import cn.arain.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemCatService itemCatSercice;
	
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem load_id(@PathVariable(required=false) Long itemId) {
		System.out.println(itemId);
		TbItem item = itemService.load_Id(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGrid load_list(@RequestParam(value="page",required=false) int pageNum,@RequestParam(value="rows",required=false) int rowNum) {
		DataGrid dataGrid = itemService.load_list(pageNum, rowNum);
		return dataGrid;
	}
	
	@RequestMapping("item/cat/list")
	@ResponseBody
	public List<DataTree> load_cat(@RequestParam(value="id",required=false)  Long pid) {
		if(pid == null) {
			pid = (long) 0;
		}
		List<DataTree> tree_01 = itemCatSercice.load_tree_01(pid);
		return tree_01;
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public ArainResult save(final TbItem item,String desc) {
		ArainResult arainResult = itemService.save_01(item, desc);
		return arainResult;	
	}
	
	@RequestMapping("/rest/page/item-edit")
	public String edit(final HttpServletRequest request,@RequestParam("itemId") Long itemId,Model model) {
		TbItem tbItem = itemService.load_item(itemId);
		//2017-04-18 12:01:49
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(tbItem.getCreated());
		model.addAttribute("time", time);
		model.addAttribute("tbItem", tbItem);
		return "item-edit";
	}
	
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public ArainResult load_desc(@PathVariable Long id) {
		TbItemDesc itemDesc = itemService.load_desc(id);
		return ArainResult.ok(itemDesc);
	}
	
	@RequestMapping("/rest/item/param/item/query/")
	@ResponseBody
	public ArainResult load_query() {
		return null;
		
	}
	
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public ArainResult update(final TbItem item,String desc,@RequestParam(value="time",required=false) String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		item.setCreated(format.parse(time));
	
		ArainResult result = itemService.save_01(item, desc);
		return result;
		
	}
	
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public ArainResult delete(@RequestParam(value="ids",required=false) Long[] ids) {
		ArainResult result = itemService.delete(ids);
		return result;
	}
}
