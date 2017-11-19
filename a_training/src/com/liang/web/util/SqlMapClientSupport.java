package com.liang.web.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.liang.web.util.limit.LimitSqlExecutor;

@Component
public class SqlMapClientSupport extends SqlMapClientDaoSupport{
	
	// 所有在spring中注入的bean 都建议定义成私有的域变量。并且要配套写上 get 和 set方法。
	@Autowired  
	private SqlMapClient sqlMapClient;
	@Autowired
	private SqlExecutor sqlExecutor;
	
	@PostConstruct        
	public void initSqlMapClient(){         
		super.setSqlMapClient(sqlMapClient);    
	}
	
	public void setEnableLimit(boolean enableLimit){
		if(sqlExecutor instanceof LimitSqlExecutor){
			((LimitSqlExecutor)sqlExecutor).setEnableLimit(enableLimit);
		}
	}

	public void initialize() throws Exception{
		if(sqlExecutor!=null){
			this.sqlMapClient=getSqlMapClientTemplate().getSqlMapClient();
			if(sqlMapClient instanceof ExtendedSqlMapClient){
				ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient).getDelegate(),
                        "sqlExecutor", SqlExecutor.class, sqlExecutor);
			}
		}
	}
	
	public Object queryForObject(String statementName) throws SQLException {
		Object result = this.queryForObject(statementName, null) ;
		return result;
	}
	
	public Object queryForObject(String statementName, Object parameterObject) throws SQLException {
		Object result = new Object() ;
		if(parameterObject != null){
			result = this.getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject);
		}
		else{
			result = this.getSqlMapClientTemplate().queryForObject(statementName);
		}
		return result;
	}
	
	public List<Object> queryForList(String statementName) throws SQLException {
		List<Object> result = this.queryForList(statementName, null) ;
		return result;
	}
	
	public List<Object> queryForList(String statementName, Object parameterObject) throws SQLException {
		List<Object> result = new ArrayList<Object>() ;
		if(parameterObject != null){
			result = this.getSqlMapClientTemplate().queryForList(statementName, parameterObject);
		}
		else{
			result = this.getSqlMapClientTemplate().queryForList(statementName);
		}
		return result;
	}
	
	public List<Object> queryForList(String statementName,int skipResults,int maxResults )
	throws SQLException {
		List<Object> result = this.queryForList(statementName, null, skipResults, maxResults) ;
		return result;
	}
	
	public List<Object> queryForList(String statementName, Object parameterObject,int skipResults,int maxResults)
	throws SQLException {
		
		List<Object> result = new ArrayList<Object>() ;
		
		if(parameterObject != null){
			result = this.getSqlMapClientTemplate().queryForList(statementName, parameterObject, skipResults, maxResults);
		}
		else{
			result = this.getSqlMapClientTemplate().queryForList(statementName, skipResults, maxResults);
		}
		return result;
	}
	
	public Object insert(String statementName) throws SQLException {
		Object result = this.insert(statementName, null) ;
		return result;
	}	
	
	public Object insert(String statementName, Object parameterObject) throws SQLException {
		Object result = new Object() ;
		if(parameterObject != null){
			result = this.sqlMapClient.insert(statementName, parameterObject) ;
		}
		else{
			result = this.sqlMapClient.insert(statementName) ;
		}
		return result;
	}
	
	public Object update(String statementName) throws SQLException {
		Object result = this.update(statementName, null) ;
		return result;
	}	
	
	public Object update(String statementName, Object parameterObject) throws SQLException {
		Object result = new Object() ;
		if(parameterObject != null){
			result = this.sqlMapClient.update(statementName, parameterObject) ;
		}
		else{
			result = this.sqlMapClient.update(statementName) ;
		}
		return result;
	}
	
	public Object delete(String statementName) throws SQLException {
		Object result = this.delete(statementName, null) ;
		return result;
	}	
	
	public Object delete(String statementName, Object parameterObject) throws SQLException {
		Object result = new Object() ;
		if(parameterObject != null){
			result = this.sqlMapClient.delete(statementName, parameterObject) ;
		}
		else{
			result = this.sqlMapClient.delete(statementName) ;
		}
		return result;
	}
	
	public Object insertForList(String statementName, List<Object> list) throws SQLException {
		this.sqlMapClient.startBatch();
		int batch = 0;
		for (Object object : list) {					
			sqlMapClient.insert(statementName, object); 
			batch++;
		    //每1000条批量提交一次。 
		    if(batch==1000){ 
		    	sqlMapClient.executeBatch(); 
		    	sqlMapClient.startBatch();
			    batch = 0; 
		    } 
		}
		this.sqlMapClient.executeBatch();
		return null;
	}
	
	public Object updateForList(String statementName, List<Object> list) throws SQLException {
		this.sqlMapClient.startBatch();
		int batch = 0;
		for (Object object : list) {					
			sqlMapClient.update(statementName, object); 
			batch++;
		    //每1000条批量提交一次。 
		    if(batch==1000){ 
		    	sqlMapClient.executeBatch(); 
		    	sqlMapClient.startBatch();
			    batch = 0; 
		    } 
		}
		this.sqlMapClient.executeBatch();
		return null;
	}
	
	public Object deleteForList(String statementName, List<Object> list) throws SQLException {
		this.sqlMapClient.startBatch();
		int batch = 0;
		for (Object object : list) {					
			sqlMapClient.delete(statementName, object); 
			batch++;
		    //每1000条批量提交一次。 
		    if(batch==1000){ 
		    	sqlMapClient.executeBatch(); 
		    	sqlMapClient.startBatch();
			    batch = 0; 
		    } 
		}
		this.sqlMapClient.executeBatch();	
		return null ;
	}

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	
	public void startTransaction() throws SQLException {
		sqlMapClient.startTransaction();
	}

	public void commitTransation() throws SQLException {
		sqlMapClient.commitTransaction();
	}

	public void endTransation() throws SQLException {
		sqlMapClient.endTransaction();
	}
	
	public Connection getCurrentConnection() throws SQLException{
		return sqlMapClient.getCurrentConnection();
	}
	
	public Connection getConnection(){
		try {
			return this.getSqlMapClientTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
