package com.liang.sys.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liang.sys.bean.AdminBean;
import com.liang.sys.bean.Page;
import com.liang.sys.dao.InfoDao;
import com.liang.sys.service.InfoSer;
import com.liang.web.util.ObjectBindUtil;

@Service
public class InfoSerImpl implements InfoSer {

	@Autowired
	private InfoDao infoDao;

	@Override
	public String findUser(HttpServletRequest request,
			HttpServletResponse response) {

		// 获取登录用户名和密码
		Map<String, String> hm = new LinkedHashMap<String, String>();
		hm.put("username", request.getParameter("username"));
		hm.put("password", request.getParameter("password"));

		AdminBean user = (AdminBean) infoDao.getInfoObject(hm,"sys.login.findUser");

		if(user == null){
			return "登录失败，请与管理员联系";
		}else{
			LinkedHashMap<String, String> info = new LinkedHashMap<String, String>();
			String IP = this.getRemortIP(request);
			info.put("USER_NO", user.getUserNo());
			info.put("IP", IP);
			infoDao.saveInfo(info,"sys.login.addLoginInfo");
			request.getSession().setAttribute("LoginUser", user);
		}
		return "1";
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

	/**
	 * 查询信息
	 */
	@Override
	public List getInfoList(Map map,String target) {
		return infoDao.getInfoList(map,target);
	}


	/**
	 * 查询信息
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getInfoList(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		return infoDao.getInfoList(paramMap,target);
	}
	
	/**
	 * 分页查询信息
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getInfoListByPage(HttpServletRequest request, String target, Page page) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("startPage", page.getStartIndex());
		paramMap.put("numPerPage", page.getNumPerPage());
		String orderDirection = page.getOrderDirection();
		if(orderDirection == null || orderDirection.trim().equals("")) {  
            orderDirection = "asc";  
        }
		paramMap.put("orderDirection", orderDirection);
		return infoDao.getInfoListByPage(paramMap,target);
	}
	
	/**
	 * 新增信息
	 */
	@Override
	public int addInfo(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.infoDao.addInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int addStartPageInfo(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.infoDao.addStartPageInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息
	 */
	@Override
	public String addUserInfo(Map map,String target) {
		String result = "OK";
		try {
			result = this.infoDao.addUserInfo(map,target);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}
	
	/**
	 * 新增信息
	 */
	@Override
	public AdminBean login(Map map,String target) {
		AdminBean admin = null;
		try {
			admin = this.infoDao.login(map,target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}
	
	/**
	 * 新增设备信息
	 */
	@Override
	public String addMachineInfo(Map map,String target) {
		String result = "OK";
		try {
			result = this.infoDao.addMachineInfo(map,target);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}
	
	/**
	 * 更新信息
	 * @param request
	 * @param target
	 * @return
	 */
	@Override
	public int updateInfo(Map<String, String> paramMap,String target) {
		try {
			this.infoDao.updateInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 删除用户信息
	 */
	@Override
	public int deleteInfo(String SEQ, String target) {
		LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("SEQ", SEQ);
		try {
			this.infoDao.deleteInfo(paramMap, target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除设备信息
	 */
	@Override
	public String deleteMachineInfo(Map map,String target) {
		String result = "OK";
		try {
			result = this.infoDao.deleteMachineInfo(map,target);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}
}
