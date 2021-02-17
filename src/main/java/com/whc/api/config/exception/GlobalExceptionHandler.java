package com.whc.api.config.exception;

import com.alibaba.fastjson.JSONObject;

import com.whc.api.util.constants.ResultCode;
import com.whc.api.vo.ResultVO;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: whc
 * @description: 统一异常拦截
 * @date: 2021/2/17 17:40
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ExceptionHandler(value = Exception.class)
	public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) {
		String errorPosition = "";
		//如果错误堆栈信息存在
		if (e.getStackTrace().length > 0) {
			StackTraceElement element = e.getStackTrace()[0];
			String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
			int lineNumber = element.getLineNumber();
			errorPosition = fileName + ":" + lineNumber;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", ResultCode.E_400.getCode());
		jsonObject.put("msg", ResultCode.E_400.getMsg());
		JSONObject errorObject = new JSONObject();
		errorObject.put("errorLocation", e.toString() + "    错误位置:" + errorPosition);
		jsonObject.put("info", errorObject);
		logger.error("异常", e);
		return jsonObject;
	}

	/**
	 * 本系统自定义错误的拦截器
	 * 拦截到此错误之后,就返回这个类里面的json给前端
	 * 常见使用场景是参数校验失败,抛出此错,返回错误信息给前端
	 */
	@ExceptionHandler(CommonJsonException.class)
	public JSONObject commonJsonExceptionHandler(CommonJsonException commonJsonException) {
		return commonJsonException.getResultJson();
	}


	/**
	 * 权限不足报错拦截
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResultVO<String> unauthorizedExceptionHandler() {
		// 然后提取错误提示信息进行返回
		return new ResultVO<>(ResultCode.E_502);
	}

	/**
	 * 用户名不存在
	 */
	@ExceptionHandler(UnknownAccountException.class)
	public ResultVO<String> unknownAccountException(){
		return new ResultVO<>(ResultCode.E_20001);
	}

	/**
	 * 密码错误
	 */
	@ExceptionHandler(IncorrectCredentialsException.class)
	public ResultVO<String> incorrectCredentialsException(){
		return new ResultVO<>(ResultCode.E_20002);
	}
	/**
	 * 未登录报错拦截
	 * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
	 */
	@ExceptionHandler(UnauthenticatedException.class)
	public ResultVO<String> unauthenticatedException() {
		return new ResultVO<>(ResultCode.E_20011);
	}


}
