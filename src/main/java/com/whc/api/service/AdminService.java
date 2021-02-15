package com.whc.api.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

public interface AdminService {
    JSONObject setBan(JSONObject requestJason);
}
