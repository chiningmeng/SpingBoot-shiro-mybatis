package com.whc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.whc.api.service.AdminService;
import com.whc.api.util.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "设置禁言",notes = "参数要求 openid（被禁言用户id） ，reason（禁言原因） ，duration(禁言时长，单位（天）)")
    @RequiresPermissions("admin:setBan")
    @PostMapping("/setBan")
    public JSONObject setBan(@RequestBody JSONObject requestJason){

        //需要要被禁言的openid，原因，时长
        CommonUtil.hasAllRequired(requestJason,"openid,reason,duration");
        return adminService.setBan(requestJason);
    }
    @ApiOperation(value = "查看禁言列表",notes = "参数要求 pageNum（当前页数），pageRow（页容量）")
    @RequiresPermissions("admin:getBanInfo")
    @PostMapping("/getBanInfo")
    public JSONObject getBanInfo(@RequestBody JSONObject requestJason){
        return adminService.getBanInfo(requestJason);
    }
}
