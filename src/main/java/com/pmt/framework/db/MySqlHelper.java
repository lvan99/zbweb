package com.pmt.framework.db;



/**
 * MYSQL 数据库帮助类
 * @author hengjiu
 * @date 2013-12-13
 */
public class MySqlHelper {
	/**
	 * 分页语句
	 * @param sql 查询语句
	 * @param currPage 当前页
	 * @param pageSize 每页条数
	 * @return 分页查询语句
	 */
	public static String getPageSql(String sql, int currPage, int pageSize) {
		// 如果当前页和每页数任意之一为0，则全部
		if (currPage == 0 || pageSize == 0) {
			return sql;
		} else {
			// 计算起始行下标，startIndex
			int startIndex = (currPage - 1) * pageSize;

			return sql + " limit " + startIndex + "," + pageSize;
		}
	}
	
	/**
	 * 查询表字段信息语句
	 * @param tableName
	 * @return
	 */
	public static String getColumnSql(String tableName){
		String tableColumnSql = "select column_name from information_schema.columns where table_schema='weixin' and table_name='"+tableName+"'";
		return tableColumnSql;
	}

}
