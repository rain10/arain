package cn.arain.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.arain.common.util.FastDFSClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class UploadFile {
	
	@Value("${IMAGES_VALUE}")
	private String imageURL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String upload(final MultipartFile uploadFile,
			final HttpServletRequest request) {
	
			JSONObject jsonObject = new JSONObject();
			try {
				String filename = uploadFile.getOriginalFilename();
				String substring = filename.substring(filename.indexOf(".")+1);
				FastDFSClient fastDFSClient = new FastDFSClient("classpath:/conf/client.conf");
				String url = fastDFSClient.uploadFile(uploadFile.getBytes(),substring, null);
				
				url = imageURL+url;
				
				jsonObject.accumulate("error", 0);
				jsonObject.accumulate("url", url);
				
				return jsonObject.toString();
			} catch (Exception e) {
				e.printStackTrace();
				
				jsonObject.accumulate("error", 1);
				jsonObject.accumulate("url", "上传失败！");
			
				return jsonObject.toString();
				
			}
		
		
	}
	
}
