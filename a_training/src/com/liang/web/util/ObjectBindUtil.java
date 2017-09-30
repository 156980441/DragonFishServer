/*
 * @(#)ObjectBindUtil.java 1.0 2006-12-6 上午11:19:54
 *
 *Copyright 2001 - 2006 AIT. All Rights Reserved.
 */
package com.liang.web.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.fanyl.domain.User;


/**
 * Copyright: AIT (c) Company: AIT
 * 
 * @author kelly (wangliwei@ait.net.cn)
 * @Date 2006-12-6 上午11:19:54
 * @version 1.0
 * 
 */
public class ObjectBindUtil {

	private Logger logger = Logger.getLogger(ObjectBindUtil.class);
	
	private ObjectBindUtil() {
		/* empty */
	}
	
	/**
	 * bind parameter data to Map object from Request Object
	 * 
	 * @param req
	 * @return LinkedHashMap
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap getRequestParamData(HttpServletRequest request) {
		LinkedHashMap data = new LinkedHashMap() ;
		Enumeration e = request.getParameterNames() ;
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			data.put(key, request.getParameter(key)) ;
		}
		if(request.getParameter("dwz.person.personId") != null){
			data.put("dwzPersonId", request.getParameter("dwz.person.personId"));
		}
		
		//获取附件信息
		String[] fileName = request.getParameterValues("fileName");
		String[] fileUrl = request.getParameterValues("fileUrl");
		if(fileName !=null && fileUrl !=null){
			String fileNameStr = "";
			String fileUrlStr = "";
			for(int i = 0;i< fileName.length;i++ ){
				if(i == 0){
					fileNameStr = fileName[i];
				}else{
					fileNameStr += ";" + fileName[i];
				}
			}
			for(int i = 0;i< fileUrl.length;i++ ){
				if(i == 0){
					fileUrlStr = fileUrl[i];
				}else{
					fileUrlStr += ";" + fileUrl[i];
				}
			}
			data.put("fileName", fileNameStr);
			data.put("fileUrl", fileUrlStr);
		}
		
		//use 'language' for parameter
		User admin = SessionUtil.getLoginUserFromSession(request) ;
		//这个参数用来结合管理登陆者具有管理哪些人员类型的员工，全局搜索supervisorPersonId 可参考其他地方的用法。
		data.put("adminID", admin == null ? "" : admin.getUserNo());
		//对于具有分页功能页面来说  orderField orderDirection 两个参数是我们必须的 在此封装
		//排序列 不以页面上名称为准 区分后台传参与前台名称
		data.put("orderField", request.getParameter("orderField") != null ? request.getParameter("orderField") : "");
		//排序方式
		data.put("orderDirection", request.getParameter("orderDirection") != null ? request.getParameter("orderDirection") : "asc");
		return data;
	}
	

	
	/**
	 * bind parameter data to Map object from Request Object
	 * 
	 * @param req
	 * @return LinkedHashMap
	 */
	public static LinkedHashMap getRequestParamDataForCode(HttpServletRequest request) {
		LinkedHashMap data = new LinkedHashMap() ;
		Enumeration e = request.getParameterNames() ;
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			data.put(key, request.getParameter(key)) ;
		}
		if(request.getParameter("dwz.person.personId") != null){
			data.put("dwzPersonId", request.getParameter("dwz.person.personId"));
		}
		
		//use 'language' for parameter
		User admin = SessionUtil.getLoginUserFromSession(request) ;
		//这个参数用来结合管理登陆者具有管理哪些人员类型的员工，全局搜索supervisorPersonId 可参考其他地方的用法。
		data.put("adminID", admin == null ? "" : admin.getUserNo());
		//对于具有分页功能页面来说  orderField orderDirection 两个参数是我们必须的 在此封装
		//排序列 不以页面上名称为准 区分后台传参与前台名称
		data.put("orderField", request.getParameter("orderField") != null ? request.getParameter("orderField") : "");
		//排序方式
		data.put("orderDirection", request.getParameter("orderDirection") != null ? request.getParameter("orderDirection") : "asc");
		return data;
	}
	
	/**
	 * bind parameter data to Map object from Request Object
	 * 
	 * @param req
	 * @return LinkedHashMap
	 */
	public static LinkedHashMap getRequestParamDataNoSession(HttpServletRequest request) {
		LinkedHashMap data = new LinkedHashMap() ;
		Enumeration e = request.getParameterNames() ;
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			data.put(key, request.getParameter(key)) ;
		}
		data.put("interLanguage", "zh");
		data.put("LANGUAGE", "zh");
		return data;
	}
	
	/**
	 * bind parameter data to Map object from Request Object
	 * 
	 * @param req
	 * @return LinkedHashMap
	 */
	public static LinkedHashMap getRequestParamDataNoSession(HttpServletRequest request,String embellish) {
		LinkedHashMap data = new LinkedHashMap();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();

			if(key.indexOf(embellish)>0){
			    data.put(key.replaceAll(embellish, ""), request.getParameter(key));
			}
			else if(key.startsWith(embellish))
			{	
				data.put(key.replaceAll(embellish, ""), request.getParameter(key));
			}else{
				data.put(key, request.getParameter(key)) ;
			}
		}
		data.put("interLanguage", "zh");
		data.put("LANGUAGE", "zh");
		return data;
	}
	/**
	 * bind parameter data to Map object from Request Object
	 * 
	 * @param req
	 * @return LinkedHashMap
	 */
	public static LinkedHashMap<String, String> getRequestParamData(HttpServletRequest request,String embellish) {
		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();

			if(key.indexOf(embellish)>0){
			    data.put(key.replaceAll(embellish, ""), request.getParameter(key));
			}
			else if(key.startsWith(embellish))
			{	
				data.put(key.replaceAll(embellish, ""), request.getParameter(key));
			}else{
				data.put(key, request.getParameter(key)) ;
			}
		}

		
		//获取附件信息
		String[] fileName = request.getParameterValues("fileName");
		String[] fileUrl = request.getParameterValues("fileUrl");
		if(fileName !=null && fileUrl !=null){
			String fileNameStr = "";
			String fileUrlStr = "";
			for(int i = 0;i< fileName.length;i++ ){
				if(i == 0){
					fileNameStr = fileName[i];
				}else{
					fileNameStr += ";" + fileName[i];
				}
			}
			for(int i = 0;i< fileUrl.length;i++ ){
				if(i == 0){
					fileUrlStr = fileUrl[i];
				}else{
					fileUrlStr += ";" + fileUrl[i];
				}
			}
			data.put("fileName", fileNameStr);
			data.put("fileUrl", fileUrlStr);
		}
		
		//use 'language' for parameter
		User admin = SessionUtil.getLoginUserFromSession(request) ;
		data.put("adminID", admin == null ? "" : admin.getUserNo());
		//对于具有分页功能页面来说  orderField orderDirection 两个参数是我们必须的 在此封装
		//排序列 不以页面上名称为准 区分后台传参与前台名称
		data.put("orderField", request.getParameter("orderField") != null ? request.getParameter("orderField") : "");
		//排序方式
		data.put("orderDirection", request.getParameter("orderDirection") != null ? request.getParameter("orderDirection") : "asc");
		return data;
	}
	
	/**
	 * bind json string to List<Map> object 
	 * 
	 * @param req
	 * @return List<LinkedHashMap<String, Object>>
	 */
	public static List<LinkedHashMap<String, Object>> getRequestJsonData(String jsonString, String status, LinkedHashMap<String,Object> appendMap) {
		
		List<LinkedHashMap<String, Object>> returnList = new ArrayList(10) ; 

		try {
			
			List<LinkedHashMap<String, Object>> jsonDataList = JsonUtil.getUpdateList(jsonString) ;  
			
			if (status == null && appendMap == null){
				returnList = jsonDataList ;
			}
			else{
				int jsonDataListSzie = jsonDataList.size() ;
				  
				for (int i = 0 ; i < jsonDataListSzie ; ++i) 
				{  
					LinkedHashMap<String, Object> map = jsonDataList.get(i);
					
					//System.out.println(map);
					
					if (status != null){
						String __status = ObjectUtils.toString(map.get("__status")) ;
						if(__status.equals(status)){
							if (appendMap != null){
								map.putAll(appendMap) ;
							}
							
							returnList.add(map) ;
						}
					}
					else{
						if (appendMap != null){
							map.putAll(appendMap) ;
						}
						returnList.add(map) ;
					}
				}  
			}
			
		} 
		catch (JsonParseException e){  
			e.printStackTrace();  
		} 
		catch (JsonMappingException e){  
			e.printStackTrace();  
		} 
		catch (IOException e){  
			e.printStackTrace();  
		}
		catch (Exception e){  
			e.printStackTrace();  
		}
		
		return returnList ;
	}
	
	/**
	 * bind json string to List<Map> object 
	 * 
	 * @param req
	 * @return List<LinkedHashMap<String, Object>>
	 */
	public static List<LinkedHashMap<String, Object>> getRequestJsonData(String jsonString, String status) {
		
		return getRequestJsonData(jsonString, status, null) ;
	}
	
	/**
	 * bind json string to List<Map> object 
	 * 
	 * @param req
	 * @return List<LinkedHashMap<String, Object>>
	 */
	public static List<LinkedHashMap<String, Object>> getRequestJsonData(String jsonString) {
		
		return getRequestJsonData(jsonString, null, null) ;
	}
	
	/**
	 * bind json string to List<Map> object 
	 * 
	 * @param req
	 * @return List<LinkedHashMap<String, Object>>
	 */
	public static List<LinkedHashMap<String, Object>> getRequestJsonData(String jsonString, LinkedHashMap<String,Object> appendMap) {
		
		return getRequestJsonData(jsonString, null, appendMap) ;
	}
	
}
