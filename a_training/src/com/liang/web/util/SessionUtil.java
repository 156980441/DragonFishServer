package com.liang.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fanyl.domain.User;

public class SessionUtil {

	private static SessionUtil instance;
	
	public SessionUtil() {
		if (instance == null) {
			getInstance();
		}
	}

	public static SessionUtil getInstance() {
		return new SessionUtil();
	}
	
	/**
	 * get session LoginUser
	 */
	public static User getLoginUserFromSession(HttpServletRequest request) {
		// session用户信息
		HttpSession session = request.getSession() ;
		/*
		 * SY_ADMIN_V,从这个视图中提取信息
		 */
		User admin = (User)session.getAttribute("LoginUser") ;
		return admin ;
	}
	
}
