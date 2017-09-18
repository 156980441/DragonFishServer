package com.fanyl.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanyl.domain.Page;
import com.fanyl.domain.User;

public interface UserDao {
	
	/*查询用户*/
	public String findUser(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 查询信息
	 */
	public List getInfoList(Map map,String target);


	/**
	 * 查询信息
	 */
	public List getInfoList(HttpServletRequest request,String target);
	
	/**
	 * 分页查询信息
	 * @param request
	 * @param target
	 * @param page
	 * @return
	 */
	public List getInfoListByPage(HttpServletRequest request,String target, Page page);
	
	/**
	 * 新增信息
	 */
	public int addInfo(HttpServletRequest request,String target);
	
	/**
	 * 更新
	 * @param request
	 * @param target
	 * @return
	 */
	public int updateInfo(Map<String, String> paramMap,String target);
	
	/**
	 * 删除用户信息
	 */
	public int deleteInfo(String SEQ, String target);
	
	/**
	 * 新增信息
	 */
	public int addStartPageInfo(HttpServletRequest request,String target);
	
	/**
	 * 新增信息
	 */
	public String addUserInfo(Map map,String target) ;
	
	/**
	 * 登录
	 */
	public User login(Map<String, String> map,String target);
	
	/**
	 * 新增用戶所持有设备
	 */
	public String addMachineInfo(Map map,String target);
	
	/**
	 * 删除设备信息
	 */
	public String deleteMachineInfo(Map map,String target);
}
