package cn.arain.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arain.common.util.ArainResult;
import cn.arain.common.util.CookieUtils;
import cn.arain.pojo.TbUser;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Value("${TOKEN_NAME}")
	private String tokenname;
	
	@RequestMapping("/page/register")
	public String reg_web() {
		
		return "register";
		
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public ArainResult checkData(@PathVariable String param, @PathVariable Integer type) {
		ArainResult ArainResult = userService.checkData(param, type);
		return ArainResult;
	}
	
	
	
	@RequestMapping("/user/register")
	@ResponseBody
	public ArainResult register(final TbUser user) {
		ArainResult result = userService.register(user);
		return result;
	}
	
	
	
	@RequestMapping("/page/login")
	public String login_web() {
		
		return "login";
		
	}
	
	@RequestMapping("/user/login")
	@ResponseBody
	public ArainResult login (final String username ,String password,HttpServletRequest request,HttpServletResponse response) {
		ArainResult result = userService.login(username, password);
		if(result.getStatus() == 200) {
		CookieUtils.setCookie(request, response,tokenname, result.getData().toString(),9999999);
		}
		return result;
	}
	
}
