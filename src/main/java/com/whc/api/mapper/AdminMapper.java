package com.whc.api.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    void setBan(JSONObject requestJason);

}
