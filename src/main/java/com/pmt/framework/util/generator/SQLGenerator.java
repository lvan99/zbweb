package com.pmt.framework.util.generator;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL语句生成工具类
 * @author Administrator
 * @date 2014-3-26
 */
public class SQLGenerator {
	
	/**
	 * 根据表名和列名组装保存SQL语句
	 * @param tableName 表名
	 * @param columnIterator 列名集合
	 * @return 保存SQL语句
	 */
	public static String getInsertSQL(String tableName, Iterator<Object> columnIterator) {
		String defaultValue = null;
		if( StringUtils.isEmpty(tableName) ) {
			System.out.println("表名不能为空");
			return defaultValue;
		}
	
		
		StringBuffer columnSQL = new StringBuffer();
		StringBuffer valuesSQL = new StringBuffer();
		int i = 0;
		while( columnIterator.hasNext() ){
			if( i > 0 ) {	//追加逗号
				columnSQL.append( "," );
				valuesSQL.append( "," );
			}
			Object columnName = columnIterator.next();
			columnSQL.append( columnName );
			valuesSQL.append( "?" );
			i++;
		}
		
		//传了列信息
		if ( i == 0 ) {
			System.out.println("列信息不能为空");
			return defaultValue;
		}
		
		StringBuffer insertSQL = new StringBuffer("INSERT INTO ").append( tableName );
		insertSQL.append( "(" ).append(columnSQL).append( ") VALUES (" ).append( valuesSQL ).append( ")" );
		System.out.println("INSERT SQL :\n"+insertSQL);
		return insertSQL.toString();
	}
	
	public static String getUpdateSQL(String tableName, String[] whereFields, Iterator<Object> columnIterator) {
		String defaultValue = null;
		if( StringUtils.isEmpty(tableName) ) {
			System.out.println("表名不能为空");
			return defaultValue;
		}	
		
		if(whereFields.length == 0) {
			System.out.println("条件字段信息不能为空");
			return defaultValue;
		}
		
		StringBuffer setSQL = new StringBuffer();
		
		int i = 0;
		while( columnIterator.hasNext() ){
			if( i > 0 ) {	//追加逗号
				setSQL.append( "," );
			}
			Object columnName = columnIterator.next();
			setSQL.append( columnName ).append( "=?" );
			i++;
		}
		
		//传了列信息
		if ( i == 0 ) {
			System.out.println("列信息不能为空");
			return defaultValue;
		}
		
		StringBuffer whereSQL = new StringBuffer();
		for(String whereField : whereFields) {
			if(whereSQL.length() > 0){
				whereSQL.append(",");
			}
			whereSQL.append( whereField ).append( "=?" );
		}
		
		StringBuffer updateSQL = new StringBuffer("UPDATE ").append( tableName );
		updateSQL.append( " SET " ).append( setSQL ).append( " WHERE " ).append( whereSQL );
		System.out.println("UPDATE SQL :\n"+updateSQL);
		return updateSQL.toString();
	}
	
}
