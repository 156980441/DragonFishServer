package com.fanyl.dao;

import java.util.List;
import java.util.Map;

public interface UniversalDao {
	public List getInfoList(Map<String, String> map, String target);
	public int updateInfo(Map<String, String> paramMap, String target);
}
