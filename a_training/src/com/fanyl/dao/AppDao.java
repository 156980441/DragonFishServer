package com.fanyl.dao;

import java.util.Map;

import com.fanyl.domain.User;

//可以存在同名的方法，但是参数不能相同，这样的关系体现了Java中的多态(重载)

public interface AppDao extends UniversalDao {
	public User login(Map<String, String> map, String target);
	public String addUserInfo(Map<String, String> map, String target);
	public String addMachineInfo(Map<String, String> map, String target);
	public String deleteMachineInfo(Map map, String target);
}
