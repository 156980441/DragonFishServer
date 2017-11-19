package com.fanyl.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanyl.dao.ServerDao;
import com.fanyl.dao.UniversalDao;
import com.fanyl.dao.UserDao2;
import com.fanyl.domain.Page;
import com.fanyl.domain.User;
import com.liang.web.util.ObjectBindUtil;

@Service
public class ServerDaoImpl implements ServerDao, UniversalDao {

	@Autowired
	private UserDao2 userDao2;
	
	@Override
	public List getInfoList(Map<String, String> map, String target) {
		return userDao2.getInfoList(map,target);
	}
	
	@Override
	public String findUser(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> hm = new LinkedHashMap<String, String>();
		hm.put("username", request.getParameter("username"));
		hm.put("password", request.getParameter("password"));

		User user = (User) userDao2.getObject(hm, "sys.login.findUser");

		if (user == null) {
			return "登录失败，请与管理员联系";
		} else {
			LinkedHashMap<String, String> info = new LinkedHashMap<String, String>();
			String IP = this.getRemortIP(request);
			info.put("USER_NO", user.getUserNo());
			info.put("IP", IP);
			userDao2.saveInfo(info, "sys.login.addLoginInfo");
			request.getSession().setAttribute("LoginUser", user);
		}
		return "1";

	}

	@Override
	public int addInfo(HttpServletRequest request, String target) {
		Map<String, String> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.userDao2.addInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateInfo(Map<String, String> paramMap, String target) {
		try {
			this.userDao2.updateInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteInfo(String SEQ, String target) {
		LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("SEQ", SEQ);
		try {
			this.userDao2.deleteInfo(paramMap, target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int addStartPageInfo(HttpServletRequest request, String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.userDao2.addStartPageInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<Object> getInfoList(HttpServletRequest request, String target) {
		Map<String, String> paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		return userDao2.getInfoList(paramMap,target);
	}

	@Override
	public List<Object> getObjectListByPage(HttpServletRequest request, String target, Page page) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("startPage", page.getStartIndex());
		paramMap.put("numPerPage", page.getNumPerPage());
		String orderDirection = page.getOrderDirection();
		if(orderDirection == null || orderDirection.trim().equals("")) {  
            orderDirection = "asc";  
        }
		paramMap.put("orderDirection", orderDirection);
		return userDao2.getInfoListByPage(paramMap,target);
	}

	@Override
	public List getInfoListByPage(Object object, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	// 取得实际IP
		public String getRemortIP(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
		}
}
