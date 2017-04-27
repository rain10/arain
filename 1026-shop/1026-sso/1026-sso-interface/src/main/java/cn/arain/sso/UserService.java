package cn.arain.sso;

import cn.arain.common.util.ArainResult;
import cn.arain.pojo.TbUser;

public interface UserService {
	public ArainResult checkData(String param, int type);
	public ArainResult register(TbUser user);
	public ArainResult login(String username,String  password);
	public ArainResult getUserByToken(String token);
}
