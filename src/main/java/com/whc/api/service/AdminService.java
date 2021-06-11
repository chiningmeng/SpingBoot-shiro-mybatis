package com.whc.api.service;

import com.alibaba.fastjson.JSONObject;
import com.whc.api.enums.BanDuration;
import com.whc.api.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class AdminService{
    @Autowired
    private AdminMapper adminMapper;

/*
* 在@Transactional注解中如果不配置rollbackFor属性,
* 那么事物只会在遇到RuntimeException的时候才会回滚,
* 加上rollbackFor=Exception.class,
* 可以让事物在遇到非运行时异常时也回滚
* */



    /**
     * @author whc
     * @param adminId: 操作员openid
     * @param openid: 被禁言者openid
     * @param reason: 原因，整形
     * @param durationIndex: 禁言时间的索引 时长参数以1、2、3、4、5代替1天，3天，7天，一个月，永久
     * @return com.alibaba.fastjson.JSONObject
     * @date 2021/6/11 9:21
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject setBan(String adminId,
                             String openid,
                             int reason,
                             int durationIndex) {

        int duration = BanDuration.values()[durationIndex-1].getDuration();

        //计算结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE,duration);
        Date date = calendar.getTime();

        //转换日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String end = dateFormat.format(date);

        adminMapper.setBan(openid,end,adminId,reason);
        return new JSONObject();
    }


    public List<JSONObject> getBanInfo() {

        updateBanInfo();
        return adminMapper.getBanInfo();
    }

    /**
     * 删除过时禁言信息
     */
    public void updateBanInfo() {
        adminMapper.updateBanInfo();
    }




}
