package com.whc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.whc.api.service.AdminService;
import com.whc.api.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequiresPermissions("admin:setBan")
    @PostMapping("/setBan")
    public JSONObject setBan(@RequestBody JSONObject requestJason){

        //需要要被禁言的openid，原因，时长
        CommonUtil.hasAllRequired(requestJason,"openid,reason,duration");
        return adminService.setBan(requestJason);
    }
}
