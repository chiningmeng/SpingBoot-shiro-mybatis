package com.whc.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.whc.api.mapper.LoginMapper;
import com.whc.api.service.LoginService;
import com.whc.api.service.PermissionService;
import com.whc.api.util.CommonUtil;
import com.whc.api.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private PermissionService permissionService;

    /**
     * 登录表单提交
     */
    @Override
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        //得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //shiro提供的login方法--》自定义Realm中的doGetAuthenticationInfo
            currentUser.login(token);
            info.put("result", "success");
        } catch (UnknownAccountException e){
            info.put("result","用户名不存在");
        }catch (IncorrectCredentialsException e){
            info.put("result","密码错误");
        }
        catch (AuthenticationException e) {
            info.put("result", "fail");
        }

        return CommonUtil.successJson(info);
    }

    /**
     * 根据用户名和密码查询对应的用户
     */
    @Override
    public JSONObject getUser(String username) {
        return loginMapper.getUser(username);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject info = new JSONObject();
        JSONObject userPermission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        info.put("userPermission", userPermission);
        return CommonUtil.successJson(info);
    }

    /**
     * 退出登录
     */
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtil.successJson();
    }
}
