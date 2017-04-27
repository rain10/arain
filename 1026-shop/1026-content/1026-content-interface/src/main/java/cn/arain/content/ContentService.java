package cn.arain.content;

import java.util.List;

import cn.arain.common.util.ArainResult;
import cn.arain.pojo.TbContent;

public interface ContentService {
	//后台
	ArainResult save(TbContent content);
	ArainResult edit(TbContent content);
	ArainResult delete(Long[] ids);
	
	
	//前台
	List<TbContent> load_ad(Long categoryId);
}
