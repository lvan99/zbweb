package com.pmt.framework.db;

/**
 * Oracle 数据库帮助类
 * @author hengjiu
 * @date 2013-12-13
 */
public class OracleHelper {
	/**
	 * 分页语句
	 * @param sql 查询语句
	 * @param currPage 当前页
	 * @param pageSize 每页条数
	 * @return 分页查询语句
	 */
	public static String getPageSql(String sql, int currPage, int pageSize) {
		if (currPage == 0 || pageSize == 0) {
			return sql;
		} else {
			// 计算起始行下标，startIndex
			int startIndex = (currPage - 1) * pageSize;
			// 计算截至下标,endIndex
			int endIndex = startIndex + pageSize;
			//修复Oracle分页查询必须以唯一列排序解决不同页数据重复问题
			return "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (" + sql
					+ ") A 	WHERE ROWNUM <= " + endIndex + " ORDER BY ROWNUM)	WHERE RN >= "
					+ (startIndex+1);
		}
	}
}
