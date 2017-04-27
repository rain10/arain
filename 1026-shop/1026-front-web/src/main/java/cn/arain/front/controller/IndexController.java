package cn.arain.front.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.arain.content.ContentService;
import cn.arain.pojo.TbContent;

/**
 * 
* <p>Title:IndexController </p>
* @author Arain
* @date2017年4月19日
 */

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@Value("${AD1}")
	private Long categoryId;
	
	@RequestMapping("index.html")
	public String index(final Model model,final HttpServletRequest request) {
		List<TbContent> ad = contentService.load_ad(categoryId);
		model.addAttribute("ad1List", ad);
		return "index";
	}
}
