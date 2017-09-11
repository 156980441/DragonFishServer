package com.liang.sys.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.liang.sys.bean.AdminBean;
import com.liang.sys.dao.InfoDao;
import com.liang.web.util.SqlMapClientSupport;
import com.liang.web.util.StringUtil;

@Repository
public class InfoDaoImpl extends SqlMapClientSupport implements InfoDao {

	@Override
	public List getInfoList(Object object, String target) {
		List result = null;
		try {
			result = this
					.queryForList( target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 分页查询信息
	 */
	@Override
	public List getInfoListByPage(Object object, String target) {
		List result = null;
		try {
			result = this
					.queryForList( target, object);
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
	public Object getInfoObject(Object object, String target) {
		
		Object obj = null;
		
		try {
			List result = this.queryForList(target, object);
			
			if(result != null && result.size() > 0 ){
				obj = result.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int addInfo(Object object,String target) throws Exception {
		Map obj=(Map)object;
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
	
	/**
	 * 更新
	 */
	@Override
	public int updateInfo(Object object,String target) throws Exception {
		Map obj=(Map)object;
		this.update(target, obj);
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

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
	@Override
	public String addUserInfo(Object object,String target) throws Exception {
		String resultStr = "OK";
		try {
			List result = this.queryForList( "sys.login.findUser", object);
			if( result != null && result.size() > 0){
				resultStr = "用户名已存在";
			}else{
				this.updateForList( target,(List)object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resultStr = "ERROR";
		}
		return resultStr;
	}

	/**
	 * 登录
	 * 
	 * @param Object
	 * @return
	 */
	@Override
	public AdminBean login(Object object,String target) throws Exception {
		AdminBean admin = null;
		try {
			List result = this.queryForList( "sys.login.findUserForApp", object);
			if(result != null && result.size() > 0){
				admin = (AdminBean)result.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}

	/**
	 * 添加
	 * 
	 * @param Object
	 * @return
	 */
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

	/**
	 * 删除操作
	 * @param object
	 * @param target
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteInfo(Object object,String target) throws Exception {
		Map obj=(Map)object;
		this.delete(target, obj);
		return 1;
	}

	@Override
	public String deleteMachineInfo(Object object, String target)
			throws Exception {
		String resultStr = "OK";
		try {
			this.delete(target, object);
		}catch(Exception e) {
			resultStr = "ERROR";
		}
		return resultStr;
	}
}
