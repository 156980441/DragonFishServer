package com.liang.web.util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.ui.ModelMap;

@Aspect
public class PageInterceptor {
	
	// 切入点过滤条件
	@After("execution(* com.liang.*.action.*.view*List(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,org.springframework.ui.ModelMap)) and args(request,response,modelMap)"
			+ "||execution(* com.liang.*.action.*.*.view*List(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,org.springframework.ui.ModelMap)) and args(request,response,modelMap)"
			+ "||execution(* com.liang.web.login.*.view*List(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,org.springframework.ui.ModelMap)) and args(request,response,modelMap)")
	
	public void invokeMethod(HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Throwable {

		modelMap.put(UiUtil.PAGE_NUM_NAME, UiUtil.getPageNum(request));
		modelMap.put(UiUtil.NUM_PER_PAGE_NAME, UiUtil.getNumPerPage(request));
		modelMap.addAllAttributes((Collection<?>) ObjectBindUtil.getRequestParamData(request, "seach_"));
	}
}
