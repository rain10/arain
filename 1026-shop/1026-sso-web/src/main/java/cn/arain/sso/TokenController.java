package cn.arain.sso;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.arain.common.util.ArainResult;
import cn.arain.common.util.JsonUtils;

@Controller
public class TokenController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/user/token/{token}",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String token(@PathVariable String token,String callback) {
		ArainResult result = userService.getUserByToken(token);
		
		if(StringUtils.isNotBlank(callback)) {
//			MappingJacksonValue value = new MappingJacksonValue(result);
//			value.setJsonpFunction(callback);
//			return value;
			String tokenResult = callback+"("+JsonUtils.objectToJson(result)+");";
			return tokenResult.toString();
		}
		
		return result.toString();
	}
}
