package com.whc.api.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    void setBan(@Param("openid") String openid,
                @Param("end") String end,
                @Param("adminId") String adminId,
                @Param("reason") int reason);

    List<JSONObject> getBanInfo();

    void updateBanInfo();

    void deleteMessage();
}
