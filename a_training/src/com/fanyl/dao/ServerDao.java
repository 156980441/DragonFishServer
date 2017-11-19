package com.fanyl.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanyl.domain.Page;

public interface ServerDao extends UniversalDao {
	public String findUser(HttpServletRequest request, HttpServletResponse response);
	public List<Object> getInfoList(HttpServletRequest request, String target);
	public List<Object> getObjectListByPage(HttpServletRequest request, String target, Page page);
	public List<Object> getInfoListByPage(Object object, String target);
	public int addInfo(HttpServletRequest request, String target);
	public int deleteInfo(String SEQ, String target);
	public int addStartPageInfo(HttpServletRequest request, String target);
}
