package com.whc.api.config.shiro;

import com.alibaba.fastjson.JSONObject;

import com.whc.api.util.constants.ErrorEnum;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: whc
 * @description: 对没有登录的请求进行拦截, 全部返回json信息. 覆盖掉shiro原本的跳转login.jsp的拦截方式
 * @date: 2017/10/24 10:11
 */
public class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter {

	//在org.apache.shiro.web.filter.AccessControlFilter中的onPreHandle执行时，若isAccessAllowed返回false，则执行onAccessDenied
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", ErrorEnum.E_20011.getErrorCode());
		jsonObject.put("msg", ErrorEnum.E_20011.getErrorMsg());
		PrintWriter out = null;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			res.setCharacterEncoding("UTF-8");
			//content-type是响应消息报头中的一个参数，标识响应正文数据的格式
			// application/json   JSON数据格式
			res.setContentType("application/json");
			out = response.getWriter();
			out.println(jsonObject);
		} catch (Exception e) {
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
		return false;
	}
	//解决Shiro+SpringBoot自定义Filter不生效问题
//在SpringBoot+Shiro实现安全框架的时候，自定义扩展了一些Filter，并注册到ShiroFilter，
// 但是运行的时候发现总是在ShiroFilter之前就进入了自定义Filter，结果当然是不对的。
	//https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-disable-registration-of-a-servlet-or-filter
//经过查看相关文档，发现其实是SpringBoot自动帮我们注册了我们的Filter,典型的好心办坏事。
// 我们要的是希望Shiro来管理我们的自定义Filter，所以我们要想办法取消SpringBoot自动注册我们的Filter。
	//所以解决办法是另外多定义一份配置文件告诉SpringBoot不要自作多情：
	@Bean
	public FilterRegistrationBean registration(AjaxPermissionsAuthorizationFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean(filter);
		registration.setEnabled(false);
		return registration;
	}
}
