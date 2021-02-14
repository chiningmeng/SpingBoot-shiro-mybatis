package com.whc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.whc.api.service.LoginService;
import com.whc.api.util.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */

    @ApiOperation(value = "登录",notes = "参数要求 String username,String password")
    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username,password");

        return loginService.authLogin(requestJson);
    }

    /**
     * 查询当前登录用户的信息
     */
    @ApiOperation("查询当前登录用户的信息")
    @PostMapping("/getInfo")
    public JSONObject getInfo() {
        return loginService.getInfo();
    }

    /**
     * 登出
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public JSONObject logout() {
        return loginService.logout();
    }
}
