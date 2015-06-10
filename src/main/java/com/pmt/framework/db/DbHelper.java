package com.pmt.framework.db;

/**
 * 数据库操作类
 * @author hengjiu
 *	@date 2013-12-13
 */
public class DbHelper {
	/**
	 * 组装记录数查询语句
	 * @param sql
	 * @return
	 */
	public static String getCountQuerySql(String sql) {
		return "select count(*) from (" + sql + ") xCount";
	}
}
