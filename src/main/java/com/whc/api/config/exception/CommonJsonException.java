package com.whc.api.config.exception;



import com.alibaba.fastjson.JSONObject;


/**
 * @author: hxy
 * @description: 本系统使用的自定义错误类
 * 比如在校验参数时,如果不符合要求,可以抛出此错误类
 * 拦截器可以统一拦截此错误,将其中json返回给前端
 * @date: 2017/10/24 10:29
 */
public class CommonJsonException extends RuntimeException {
    private JSONObject resultJson;



    public CommonJsonException(JSONObject resultJson) {
        this.resultJson = resultJson;
    }

    public JSONObject getResultJson() {
        return resultJson;
    }
}
