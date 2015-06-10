package com.pmt.framework.enumeration;

public enum DbType {
	ORACLE("oracle")  ,	MYSQL("mysql"),  HSQL("hsql");
	
	private String type;
	private DbType(String value)
	{
		type = value;
	}

	@Override
	public String toString() {
		return type;
	}
	
	
}
