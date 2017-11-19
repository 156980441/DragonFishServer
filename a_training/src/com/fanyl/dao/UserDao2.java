package com.fanyl.dao;

import java.util.List;

import com.fanyl.domain.User;

public interface UserDao2 {
	
	public List<Object> getInfoList(Object object,String target);
	
	public List<Object> getInfoListByPage(Object object,String target);
	
	public int saveInfo(Object object,String target);
	
	public Object getObject(Object param, String target) ;

	public int addInfo(Object object,String target) throws Exception;
	
	public int addStartPageInfo(Object object,String target) throws Exception;

	public String addUserInfo(Object object,String target) throws Exception;
	
	public int updateInfo(Object object,String target) throws Exception;
	
	public int deleteInfo(Object object,String target) throws Exception;

	public User login(Object object,String target) throws Exception ;

	public String addMachineInfo(Object object,String target) throws Exception;
	
	public String deleteMachineInfo (Object object,String target) throws Exception;
}
