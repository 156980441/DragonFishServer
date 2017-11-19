package com.fanyl.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fanyl.dao.UserDao2;
import com.fanyl.domain.User;
import com.liang.web.util.SqlMapClientSupport;
import com.liang.web.util.StringUtil;

@Repository
public class UserDaoImpl2 extends SqlMapClientSupport implements UserDao2 {

	@Override
	public List<Object> getInfoList(Object object, String target) {
		List<Object> result = null;
		try {
			result = this.queryForList(target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Object> getInfoListByPage(Object object, String target) {
		List<Object> result = null;
		try {
			result = this.queryForList( target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int saveInfo(Object object, String target) {
		try {
			this.insert(target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	@Override
	public Object getObject(Object param, String target) {
		Object obj = null;
		try {
			List<Object> result = this.queryForList(target, param);
			if(result != null && result.size() > 0 ){
				obj = result.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public int addInfo(Object object,String target) throws Exception {
		Map<String, String> obj=(Map<String, String>)object;
		//附件上传
		if (obj.get("fileUrl") != null && !"".equals(StringUtil.checkNull(obj.get("fileUrl")))) {
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					int i = j + 1;
					obj.put("PIC_URL" + i, fileUrl[j].replace("\r\n", ""));
				}
			}
		}
		this.insert(target, obj);
		return 1;
	}
	
	@Override
	public int updateInfo(Object object,String target) throws Exception {
		Map<String,String> param=(Map<String,String>)object;
		this.update(target, param);
		return 1;
	}
	
	@Override
	public int addStartPageInfo(Object object,String target) throws Exception {
		Map obj=(Map)object;
		if (obj.get("fileUrl") != null && !"".equals(StringUtil.checkNull(obj.get("fileUrl")))) {
			String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					obj.put("PIC_URL", fileUrl[j].replace("\r\n", ""));
					break;
				}
			}
		}
		if (obj.get("fileName") != null && !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
			String[] fileUrl = StringUtil.checkNull(obj.get("fileName")).split(";");
			if (fileUrl != null && fileUrl.length > 0) {
				for (int j=0;j<fileUrl.length ;j++) {
					obj.put("PIC_NAME", fileUrl[j].replace("\r\n", ""));
					break;
				}
			}
		}
		this.insert(target, obj);
		return 1;
	}

	@Override
	public String addUserInfo(Object object,String target) throws Exception {
		String resultStr = "OK";
		try {
			List result = this.queryForList( "sys.login.findUser", object);
			if( result != null && result.size() > 0){
				resultStr = "用户名已存在";
			}else{
				this.insert( target,object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resultStr = "ERROR";
		}
		return resultStr;
	}

	@Override
	public User login(Object object,String target) throws Exception {
		User admin = null;
		try {
			List<Object> result = this.queryForList("sys.login.findUserForApp", object);
			if(result != null && result.size() > 0){
				admin = (User)result.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}

	@Override
	public String addMachineInfo(Object object,String target) throws Exception {
		String resultStr = "OK";
		try {
			List result = this.queryForList( "sys.business.viewMachineList", object);
			if( result != null && result.size() > 0){
				result = this.queryForList( "sys.business.getUserMachine", object);
				if( result != null && result.size() > 0){
					resultStr = "此设备已添加，无需重复添加";
				}else{
					this.update( target,object);
				}
			}else{
				resultStr = "设备不存在";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resultStr = "ERROR";
		}
		return resultStr;
	}

	@Override
	public int deleteInfo(Object object,String target) throws Exception {
		this.delete(target, object);
		return 1;
	}

	@Override
	public String deleteMachineInfo(Object object, String target) throws Exception {
		String resultStr = "OK";
		try {
			this.delete(target, object);
		}catch(Exception e) {
			resultStr = "ERROR";
		}
		return resultStr;
	}
}
