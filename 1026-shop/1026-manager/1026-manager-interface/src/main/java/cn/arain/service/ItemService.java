package cn.arain.service;

import cn.arain.common.pojo.DataGrid;
import cn.arain.common.util.ArainResult;
import cn.arain.pojo.TbItem;
import cn.arain.pojo.TbItemDesc;
import cn.arain.pojo.TbItemParam;

public interface ItemService {
	public TbItem load_Id(Long id);
	public DataGrid load_list(int pageNum,int rowNum);
	public ArainResult save_01(TbItem item,String desc);
	public TbItem load_item(Long id);
	public TbItemDesc load_desc(Long id);
	public TbItemParam load_parm(Long id);
	public ArainResult delete(Long[] ids);
}
