package cn.arain.content;

import java.util.List;

import cn.arain.common.pojo.DataGrid;
import cn.arain.common.pojo.DataTree;
import cn.arain.common.util.ArainResult;
import cn.arain.pojo.TbContentCategory;

public interface CategoryService {
	List<DataTree> load_tree(Long pid);
	DataGrid load_list(int pageNum,int rowNum,Long categoryId);
	ArainResult save(TbContentCategory category);
	ArainResult delete(TbContentCategory category);
	
}
