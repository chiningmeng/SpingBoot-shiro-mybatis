package com.whc.api.util.constants;


public enum ResultCode {
	SUCCESS("1000", "操作成功"),

	E_400("400", "请求处理异常，请稍后再试"),
	E_501("501", "请求路径不存在"),
	E_502("502", "权限不足"),

	E_20011("20011", "登陆已过期,请重新登陆"),
	E_20001("20001","用户名不存在"),
	E_20002("20002","密码错误"),

	E_90003("90003", "缺少必填参数");

	private String code;

	private String msg;

	ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}