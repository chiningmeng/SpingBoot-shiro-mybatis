package com.whc.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.whc.api.mapper.AdminMapper;
import com.whc.api.service.AdminService;
import com.whc.api.util.CommonUtil;
import com.whc.api.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject setBan(JSONObject requestJason) {
        //取实施禁言操作的管理员id,并向json中添加
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject adminInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String adminId = adminInfo.getString("userId");
        requestJason.put("adminId",adminId);

        String dur = requestJason.getString("duration");
        int duration = Integer.parseInt(dur);
        //计算结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE,duration);
        Date date = calendar.getTime();

        //转换日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String end = dateFormat.format(date);
        //向json中添加数据
        requestJason.put("end",end);

        adminMapper.setBan(requestJason);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject getBanInfo(JSONObject jsonObject) {

        updateBanInfo();
        CommonUtil.fillPageParam(jsonObject);
        int count = adminMapper.countBan(jsonObject);
        List<JSONObject> list = adminMapper.getBanInfo(jsonObject);
        return CommonUtil.successPage(jsonObject,list,count);
    }

    /**
     * 删除过时禁言信息
     */
    @Override
    public void updateBanInfo() {
        adminMapper.updateBanInfo();
    }

}
