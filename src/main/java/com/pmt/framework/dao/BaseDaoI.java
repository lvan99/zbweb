package com.pmt.framework.dao;

import java.util.List;
import java.util.Map;


public interface BaseDaoI {

	/**
	 * 查询
	 * @param querySql 查询语句
	 * @param params 参数<可变参数>
	 * @return 结果集
	 */
	List<Map<String, Object>> queryForList(String querySql, Object[]... params);

	/**
	 * 增加、删除、修改操作
	 * @param sql 语句
	 * @param params 参数<可变参数>
	 * @return 执行结果
	 */
	boolean execute(String sql, Object[]... params);

	Map<String, Object> query(String querySql, int currPage, int pageSize,
			Object[]... params);
	
	/**
	 * 获取表字段信息
	 * @param tableName 表名
	 * @return 以字段名为Key的Map对象
	 */
	Map<String, Map<String, Object>> getTableClumnsMap(String tableName);

	boolean save(String tableName, Map<Object, Object> dataMap);

	boolean update(String tableName, String[] whereFields,
			Map<Object, Object> dataMap);
	

}