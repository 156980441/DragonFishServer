package com.fanyl.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fanyl.dao.AppDao;
import com.fanyl.dao.UniversalDao;
import com.fanyl.dao.UserDao2;
import com.fanyl.domain.User;

@Service
public class AppDaoImpl implements AppDao, UniversalDao {

	@Autowired
	private UserDao2 userDao2;
	
	@Override
	public User login(Map<String, String> map, String target) {
		User admin = null;
		try {
			admin = this.userDao2.login(map,target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
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
	public List getInfoList(Map<String, String> map, String target) {
		return userDao2.getInfoList(map,target);
	}

	@Override
	public String addUserInfo(Map<String, String> map, String target) {
		String result = "OK";
		try {
			result = this.userDao2.addUserInfo(map,target);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}

	@Override
	public String addMachineInfo(Map<String, String> map, String target) {
		String result = "OK";
		try {
			result = this.userDao2.addMachineInfo(map,target);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}

	@Override
	public String deleteMachineInfo(Map map, String target) {
		String result = "OK";
		try {
			result = this.userDao2.deleteMachineInfo(map,target);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		return result;
	}
}
