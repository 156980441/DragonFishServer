package com.fanyl.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanyl.domain.Page;

public interface ServerDao extends UniversalDao {
	public String findUser(HttpServletRequest request, HttpServletResponse response);
	// 查询对象列表，比如获取用户列表，得到用户总数之后，在调用 getObjectList 来分页显示。
	public List<Object> getObjectList(HttpServletRequest request, String target);
	public List<Object> getObjectListByPage(HttpServletRequest request, String target, Page page);
	public List<Object> getInfoListByPage(Object object, String target);
	public int addInfo(HttpServletRequest request, String target);
	public int deleteInfo(String SEQ, String target);
	public int addStartPageInfo(HttpServletRequest request, String target);
}
