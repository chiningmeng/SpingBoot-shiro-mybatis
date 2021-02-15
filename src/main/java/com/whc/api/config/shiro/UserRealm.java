package com.whc.api.config.shiro;

import com.alibaba.fastjson.JSONObject;

import com.whc.api.service.LoginService;
import com.whc.api.service.PermissionService;
import com.whc.api.util.constants.Constants;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Collection;

/**
 * @author: hxy
 * @description: 自定义Realm
 * @date: 2017/10/24 10:06
 */

//密码的比对：
	//通过AuthenticatingRealm 的 credentialsMatcher 属性来进行密码比对

public class UserRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	private PermissionService permissionService;
	//授权
	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Session session = SecurityUtils.getSubject().getSession();
		//查询用户的权限
		JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
		logger.info("permission的值为:" + permission);
		logger.info("本用户权限为:" + permission.get("permissionList"));
		//为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
		return authorizationInfo;
	}
//认证
	/**
	 * 验证当前登录的Subject
	 * LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
														//把UsernamePasswordToken对象传过来
		//1. 把AuthenticationToken 强转为 UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) authcToken;
		//2. 从UsernamePasswordToken 中获取username
		String username = upToken.getUsername();
		//3. 查询数据库，获取username对应的用户记录
		JSONObject user = loginService.getUser(username);
		//4. 若用户不存在，则可以抛出 UnknownAccountException 异常
		//5. 根据用户信息情况，觉得是否抛出其他AuthenticationException 异常
		if (user == null) {
			//没找到帐号
			throw new UnknownAccountException("用户不存在");
		}
		//6. 根据用户情况，来构建 AuthenticationInfo 对象并返回
		//通常使用的实现类为： SimpleAuthenticationInfo
		//以下数据是从数据库中获取的
		//1) principal:认证的实体信息，可以是username，也可以是数据表对应的用户的实体类对象
		//2)credential:密码
		//3)盐值 (可有可不有，看是否加盐
		//4)realmName:当前realm对象的name,调用父类的getName()即可
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getString("username"),
				user.getString("password"),
				ByteSource.Util.bytes("salt"),//salt=username+salt,采用明文访问时，不需要此句user.getString("username")+
				getName()
		);
		//session中不需要保存密码
		user.remove("password");
		//将用户信息和权限放入session中
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute(Constants.SESSION_USER_INFO, user);
		JSONObject userPermission = permissionService.getUserPermission(username);
		session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
		return authenticationInfo;
	}

}
//https://www.w3cschool.cn/shiro/hzlw1ifd.h
//