package cn.arain.service;

import java.util.List;

import cn.arain.common.pojo.DataTree;

public interface ItemCatService {
	public String load_tree(Long pid);
	public List<DataTree> load_tree_01(long pid);
}
