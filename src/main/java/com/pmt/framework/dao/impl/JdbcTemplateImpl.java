/**   
 * 文件名 : BaseDaoImpl.java
 * 包名 : com.ctg.qdp.dao.base 
 * 描述 : 数据库操作实现类
 * 创建者 : hu_hengjiu
 * 时间 : 2013-3-11 5:55:18
 * 版本 : V1.0
 * 版权 :  2013 www.ctgpc.com.cn Inc. All rights reserved
 */
package com.pmt.framework.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.pmt.framework.constants.SystemConstants;
import com.pmt.framework.dao.BaseDaoI;
import com.pmt.framework.db.DbHelper;
import com.pmt.framework.db.MySqlHelper;
import com.pmt.framework.db.OracleHelper;
import com.pmt.framework.enumeration.DbType;
import com.pmt.framework.util.generator.SQLGenerator;

/**
 * 数据访问实现类
 */
public class JdbcTemplateImpl implements BaseDaoI {
	private JdbcTemplate jdbc;
	private String database_type;
	
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public void setDatabase_type(String database_type) {
		this.database_type = database_type;
	}

	@Override
	public List<Map<String, Object>> queryForList(String querySql, Object[]... params) {
		//语句有参数
		if(params != null && params.length > 0)
		{
			return jdbc.queryForList(querySql, params[0]);
		}
		return jdbc.queryForList(querySql);
	}
	
	@Override
	public boolean execute(String sql, Object[]... params)
	{
		//语句有参数
		if(params != null && params.length > 0)
		{
			return jdbc.update(sql, params[0]) > 0;
		}
		return jdbc.update(sql) > 0;
	}
	
	@Override
	public Map<String, Object> query(String querySql, 
			int currPage, int pageSize,Object[]... params) {		
		Map<String, Object> result = new HashMap<String, Object>();
		int total = queryForInt(querySql, params); // 总记录数
		
		// Oracle		
		if (DbType.ORACLE.toString().equals(database_type)) {
			querySql = OracleHelper.getPageSql(querySql, currPage, pageSize);
		}
		// mysql hsql
		if (DbType.MYSQL.toString().equals(database_type) || DbType.HSQL.toString().equals(database_type)) {
			querySql = MySqlHelper.getPageSql(querySql, currPage, pageSize);
		}

		result.put(SystemConstants.PAGER_KEY_TOTAL, total);
		if (total == 0) {
			result.put(SystemConstants.PAGER_KEY_ROWS, new ArrayList<Map<String, Object>>());
		} else {
			List<Map<String, Object>> list = queryForList(querySql,
					params);
			result.put(SystemConstants.PAGER_KEY_ROWS, list);
		}
		return result;
	}
	
	private int queryForInt(String sql,
			Object[]... params) {
		String countQuerySql = DbHelper.getCountQuerySql(sql);
		int count =(params != null && params.length>0) ? jdbc.queryForObject(countQuerySql, params[0], Integer.class) : jdbc.queryForObject(countQuerySql, Integer.class);
		return count;
	}
	
	@Override
	public boolean save(String tableName, Map<Object, Object> dataMap) {
		//组装语句
		LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
		linkedHashMap.putAll(dataMap);
		String insertSQL = SQLGenerator.getInsertSQL( tableName, linkedHashMap.keySet().iterator());
		Object[] paramObject = linkedHashMap.values().toArray();
		return this.execute(insertSQL, paramObject);		
	}
	
	@Override
	public boolean update(String tableName, String[] whereFields, Map<Object, Object> dataMap) {
		//临时存储修改条件字段
		int whereFieldsLen = whereFields.length;
		@SuppressWarnings("unchecked")
		Map<Object, Object>[] whereFieldsMap = new Map[whereFieldsLen];
		for(int i=0; i<whereFieldsLen; i++ ) {
			whereFieldsMap[i] = new HashMap<Object, Object>();
			String key = whereFields[i];
			whereFieldsMap[i].put( "key", key );
			whereFieldsMap[i].put( "value", dataMap.get( key ));
			dataMap.remove( key );
		}
		
		LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
		linkedHashMap.putAll(dataMap);
		String updateSQL = SQLGenerator.getUpdateSQL(tableName, whereFields, linkedHashMap.keySet().iterator());
		for(Map<Object, Object> map : whereFieldsMap ) {
			linkedHashMap.put( map.get("key"), map.get("value"));
		}
		
		Object[] paramObject = linkedHashMap.values().toArray();
		return this.execute(updateSQL, paramObject);	
	}
	
	/**
	 * 查询表字段信息
	 * @param tableName
	 * @return
	 */
	public List<Map<String, Object>> getTableColumns(String tableName) {
		String querySql = "";
		// Oracle		
		if (DbType.ORACLE.toString().equals(database_type)) {
			//querySql = OracleHelper.getPageSql(querySql, currPage, pageSize);
		}
		// mysql hsql
		if (DbType.MYSQL.toString().equals(database_type) || DbType.HSQL.toString().equals(database_type)) {
			querySql = MySqlHelper.getColumnSql(tableName);
		}
		
		return queryForList(querySql);
		
	}
	
	/**
	 * 获取表字段信息
	 * @param tableName 表名
	 * @return 以字段名为Key的Map对象
	 */
	@Override
	public Map<String, Map<String, Object>> getTableClumnsMap(String tableName) {
		String querySql = "";
		// Oracle		
		if (DbType.ORACLE.toString().equals(database_type)) {
			//querySql = OracleHelper.getPageSql(querySql, currPage, pageSize);
		}
		// mysql hsql
		if (DbType.MYSQL.toString().equals(database_type) || DbType.HSQL.toString().equals(database_type)) {
			querySql = MySqlHelper.getColumnSql(tableName);
		}
		
		List<Map<String, Object>> list = queryForList(querySql);
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		for(Map<String, Object> map : list) {
			String key = String.valueOf(map.get("COLUMN_NAME"));
			result.put(key, map);
		}
		return result;
	}
	
/*	
	@Override
	public Map<String, String[]> getMetaData(String conn_name, String sql)
	{
		Map<String, String[]> metadata = new HashMap<String, String[]>();
		JdbcTemplate template = this.getJdbcTemplate(conn_name);
		SqlRowSet srs = template.queryForRowSet(sql);
		SqlRowSetMetaData srsmd = srs.getMetaData();
		String[] columnNames = srsmd.getColumnNames();
		String[] typeNames = new String[columnNames.length];
		for(int i=0, len=columnNames.length; i<len ; i++)
		{
			typeNames[i] = srsmd.getColumnTypeName(i+1);
		}
		metadata.put(SystemConstants.COLUMN_NAMES, columnNames);
		metadata.put(SystemConstants.COLUMN_TYPE_NAMES, typeNames);
		return metadata;
	}
	
	/**
	 * 使用JdbcTemplate对象执行增加、修改、删除操作
	 * @param conn_name 数据源名(配置时指定)
	 * @param sql 执行的SQL语句
	 * @param params 语句参数
	 */
	/*@Override
	public boolean execute(String conn_name, String sql, Object[]... params) throws Exception
	{
		JdbcTemplate template = this.getJdbcTemplate(conn_name);
		if(template ==  null)
		{
			throw new Exception(conn_name+" JdbcTemplate 对象加载失败");
		}
		if(params != null && params.length > 0)
		{
		  return template.update(sql, params[0]) > 0;
		}
		else
		{
			return template.update(sql) > 0;
		}
	}
	
	*//**
	 * 使用JdbcTemplate对象批量执行增加、修改、删除操作
	 * @param conn_name 数据源名(配置时指定)
	 * @param sql 执行的SQL语句
	 * @param params 语句参数
	 *//*
	@Override
	public int[] batchExecute(String conn_name, String sql, List<Object[]> params) throws Exception
	{
		JdbcTemplate template = this.getJdbcTemplate(conn_name);
		if(template ==  null)
		{
			throw new Exception(conn_name+" JdbcTemplate 对象加载失败");
		}
		  return template.batchUpdate(sql, params);
	}

	*//**
	 * 使用JdbcTemplate对象批量执行增加、修改、删除操作
	 * @param conn_name 数据源名(配置时指定)
	 * @param sql 执行的SQL语句
	 * @param params 语句参数
	 *//*
	@Override
	public int[] batchExecute(String conn_name, String[] sql) throws Exception{
		JdbcTemplate template = this.getJdbcTemplate(conn_name);
		if(template ==  null)
		{
			throw new Exception(conn_name+" JdbcTemplate 对象加载失败");
		}
		  return template.batchUpdate(sql);
	}

	*//**
	 * 使用JdbcTemplate对象执行查询操作
	 * @param conn_name 数据源名(配置时指定)
	 * @param querySql 执行的SQL语句
	 * @param params 语句参数
	 *//*
	@Override
	public List<Map<String, Object>> query(String conn_name,
			String querySql, Object[]... params) throws Exception {
		JdbcTemplate template = this.getJdbcTemplate(conn_name);
		if(template ==  null)
		{
			throw new Exception(conn_name+" JdbcTemplate 对象加载失败");
		}
		List<Map<String, Object>> list = ((params != null && params.length>0) ?template.queryForList(querySql,
				params[0])  : template.queryForList(querySql));
		for (Map<String, Object> map : list) {
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				Object value = map.get(key);
				System.out.println(key + ":" + value);
			}
		}
		return list;
	}*/

	/**
	 * 使用JdbcTemplate对象执行查询操作（分页查询）
	 * @param conn_name 数据源名(配置时指定)
	 * @param querySql 执行的SQL语句
	 * @param params 语句参数
	 * @param currPage 当前页码
	 * @param pageSize 每页条数
	 */
	/*@Override
	public Map<String, Object> query(String conn_name, String querySql, 
			int currPage, int pageSize,Object[]... params) throws Exception {
		JdbcTemplate template = this.getJdbcTemplate(conn_name);
		if(template ==  null)
		{
			throw new Exception(conn_name+" JdbcTemplate 对象加载失败");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String dbType = this.getDbType(conn_name);
		int total = queryForInt(template, querySql, params); // 总记录数
		// Oracle
		if (DatasourceConstants.ORACLE.equals(dbType)) {
			querySql = getOraclePageSql(querySql, currPage, pageSize);
		}
		// mysql
		if (DatasourceConstants.MYSQL.equals(dbType)) {
			querySql = getMysqlPageSql(querySql, currPage, pageSize);
		}

		result.put(SystemConstants.PAGER_KEY_TOTAL, total);
		if (total == 0) {
			result.put(SystemConstants.PAGER_KEY_ROWS, new ArrayList<Map<String, Object>>());
		} else {
			List<Map<String, Object>> list = query(conn_name, querySql,
					params);
			result.put(SystemConstants.PAGER_KEY_ROWS, list);
		}
		return result;
	}

	*//**
	 * 组装mysql分页查询语句
	 * @param sql
	 * @param currPage
	 * @param pageSize
	 * @return
	 *//*
	private String getMysqlPageSql(String sql, int currPage, int pageSize) {
		// 如果当前页和每页数任意之一为0，则全部
		if (currPage == 0 || pageSize == 0) {
			return sql;
		} else {
			// 计算起始行下标，startIndex
			int startIndex = (currPage - 1) * pageSize;

			return sql + " limit " + startIndex + "," + pageSize;
		}
	}

	*//**
	 * 组装Oracle分页查询语句
	 * @param sql
	 * @param currPage
	 * @param pageSize
	 * @return
	 *//*
	private String getOraclePageSql(String sql, int currPage, int pageSize) {
		if (currPage == 0 || pageSize == 0) {
			return sql;
		} else {
			// 计算起始行下标，startIndex
			int startIndex = (currPage - 1) * pageSize;
			// 计算截至下标,endIndex
			int endIndex = startIndex + pageSize;
			//UPDATE BY hu_hengjiu 2013-8-11 修复Oracle分页查询必须以唯一列排序解决不同页数据重复问题
			return "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (" + sql
					+ ") A 	WHERE ROWNUM <= " + endIndex + " ORDER BY ROWNUM)	WHERE RN >= "
					+ (startIndex+1);
		}
	}

	*//**
	 * 查询记录数
	 * @param jdbcTemplate
	 * @param sql
	 * @param params
	 * @return
	 *//*
	private int queryForInt(JdbcTemplate jdbcTemplate, String sql,
			Object[]... params) {
		String countQuerySql = getCountQuerySql(sql);
		int count =(params != null && params.length>0) ? jdbcTemplate.queryForInt(countQuerySql, params[0]) : jdbcTemplate.queryForInt(countQuerySql);
		System.out.println("count:" + count);
		return count;
	}

	*//**
	 * 组装记录数查询语句
	 * @param sql
	 * @return
	 *//*
	private String getCountQuerySql(String sql) {
		return "select count(*) from (" + sql + ") xCount";
	}*/
	
}
