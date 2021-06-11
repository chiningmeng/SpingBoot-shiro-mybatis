package com.whc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.whc.api.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "设置禁言",notes = "参数要求 adminId(操作员id)， openid（被禁言用户id） ，reason（禁言原因） ，duration(禁言时长，单位（天）)")
    @PostMapping("/setBan")
    public JSONObject setBan(@RequestParam String adminId,
                             @RequestParam String openid,
                             @RequestParam int reason,
                             @RequestParam int duration){

        //需要要被禁言的openid，原因，时长
        return adminService.setBan(adminId,openid,reason,duration);
    }
    @ApiOperation(value = "查看禁言列表",notes = "参数要求 pageNum（当前页数），pageRow（页容量）")
    @PostMapping("/getBanInfo")
    public List<JSONObject> getBanInfo(@RequestParam int pageNum, @RequestParam int pageSize){
        // 设置分页查询参数
        PageHelper.startPage(pageNum,pageSize);

        return adminService.getBanInfo();
    }
}
