package cn.arain.common.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 
* <p>Title:DataGrid </p>
* @author Arain
* @date2017年4月7日
 */
public class DataGrid implements Serializable{
	private Long total;
	private List rows;
	
	
	
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
