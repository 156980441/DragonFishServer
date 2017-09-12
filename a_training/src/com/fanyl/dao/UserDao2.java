package com.fanyl.dao;

import java.util.List;

import com.fanyl.domain.User;

public interface UserDao2 {
	
	public List getInfoList(Object object,String target);
	
	/**
	 * 分页查询信息
	 * @param object
	 * @param target
	 * @return
	 */
	public List getInfoListByPage(Object object,String target);
	
	public int saveInfo(Object object,String target);
	
	public Object getInfoObject(Object object, String target) ;


	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	public int addInfo(Object object,String target) throws Exception;
	
	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	public int addStartPageInfo(Object object,String target) throws Exception;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	public String addUserInfo(Object object,String target) throws Exception;
	
	/**
	 * 更新
	 * @param object
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public int updateInfo(Object object,String target) throws Exception;
	
	/**
	 * 删除
	 * @param object
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public int deleteInfo(Object object,String target) throws Exception;

	/**
	 * 登录
	 * 
	 * @param Object
	 * @return
	 */
	public User login(Object object,String target) throws Exception ;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	public String addMachineInfo(Object object,String target) throws Exception;
	
	/**
	 * 删除设备信息
	 * @param object
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public String deleteMachineInfo (Object object,String target) throws Exception;
}
