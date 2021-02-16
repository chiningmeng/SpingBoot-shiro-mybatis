package com.whc.api.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    void setBan(JSONObject requestJason);



    int countBan(JSONObject jsonObject);

    List<JSONObject> getBanInfo(JSONObject jsonObject);

    void updateBanInfo();
}
