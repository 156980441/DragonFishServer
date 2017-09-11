package com.liang.sys.dao;

import java.util.List;

import com.liang.sys.bean.AdminBean;

/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName LoginDao.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:22:24
 * @version 5.0
 *
 */
public interface InfoDao {
	
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
	@SuppressWarnings("unchecked")
	public int addInfo(Object object,String target) throws Exception;
	
	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addStartPageInfo(Object object,String target) throws Exception;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	public AdminBean login(Object object,String target) throws Exception ;

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
