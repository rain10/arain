package cn.arain.sso;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.arain.common.redis.JedisClient;
import cn.arain.common.util.ArainResult;
import cn.arain.common.util.JsonUtils;
import cn.arain.mapper.TbUserMapper;
import cn.arain.pojo.TbUser;
import cn.arain.pojo.TbUserExample;
import cn.arain.pojo.TbUserExample.Criteria;
import cn.arain.sso.UserService;
/**
 * 
* <p>Title:UserServiceImpl </p>
* @author Arain
* @date2017年4月26日
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public ArainResult checkData(String param, int type) {
		// 根据不同的type生成不同的查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 1：用户名 2：手机号 3：邮箱
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return ArainResult.build(400, "数据类型错误");
		}
		// 执行查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		// 判断结果中是否包含数据
		if (list != null && list.size() > 0) {
			// 如果有数据返回false
			return ArainResult.ok(false);
		}
		// 如果没有数据返回true
		return ArainResult.ok(true);
	}

	@Override
	public ArainResult register(TbUser user) {
		// 数据有效性校验
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
				|| StringUtils.isBlank(user.getPhone())) {
			return ArainResult.build(400, "用户数据不完整，注册失败");
		}
		// 1：用户名 2：手机号 3：邮箱
		ArainResult result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return ArainResult.build(400, "此用户名已经被占用");
		}
		result = checkData(user.getPhone(), 2);
		if (!(boolean) result.getData()) {
			return ArainResult.build(400, "手机号已经被占用");
		}
		// 补全pojo的属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		String password = user.getPassword();
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());

		user.setPassword(md5DigestAsHex);

		tbUserMapper.insert(user);
		return ArainResult.ok();
	}

	@Override
	public ArainResult login(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list == null && list.size() == 0) {
			return ArainResult.build(400, "用户名或密码错误！");
		}
		
		TbUser user = list.get(0);
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!user.getPassword().equals(md5DigestAsHex)) {
			return ArainResult.build(400, "用户名或密码错误！");
		}
		
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		jedisClient.set("SESSION:"+token,JsonUtils.objectToJson(user));
		
		jedisClient.expire("SESSION:"+token, 180000);
		
		return ArainResult.ok(token);
	}

	@Override
	public ArainResult getUserByToken(String token) {
		String json = jedisClient.get("SESSION:"+token);
		if(StringUtils.isBlank(json)) {
			return ArainResult.build(201, "用户信息已过期！");
		}
		TbUser user = JsonUtils.jsonToPojo(json,TbUser.class);
		return ArainResult.ok(user);
	}

}
