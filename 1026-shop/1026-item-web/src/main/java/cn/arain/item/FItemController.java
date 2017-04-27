package cn.arain.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.arain.item.pojo.Item;
import cn.arain.pojo.TbItem;
import cn.arain.pojo.TbItemDesc;
import cn.arain.service.ItemService;
/**
 * 
* <p>Title:FItemController </p>
* @author Arain
* @date2017年4月25日
 */
@Controller
public class FItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{id}.html")
	public String fItem(@PathVariable Long id,Model model) {
		
		TbItem tbitem = itemService.load_Id(id);
		TbItemDesc desc = itemService.load_desc(id);
		
		Item item = new Item(tbitem);
		
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", desc);
		
		return "item";
	}
}
