package cn.arain.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 
* <p>Title:ContentController </p>
* @author Arain
* @date2017年4月20日
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arain.common.util.ArainResult;
import cn.arain.content.ContentService;
import cn.arain.pojo.TbContent;
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/save")
	@ResponseBody
	public ArainResult add(final HttpServletRequest request,final TbContent content) {
		ArainResult result = contentService.save(content);
		return result;
	}
	

	@RequestMapping("/content/edit")
	@ResponseBody
	public ArainResult edit(final HttpServletRequest request,final TbContent content) {
		ArainResult result = contentService.edit(content);
		return result;
	}
	
	

	@RequestMapping("/content/delete")
	@ResponseBody
	public ArainResult delete(final HttpServletRequest request,@RequestParam(value="ids",required=false) Long[] ids) {
		ArainResult result = contentService.delete(ids);
		return result;
	}
}
