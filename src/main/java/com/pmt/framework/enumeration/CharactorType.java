package com.pmt.framework.enumeration;

public enum CharactorType {
	UTF_8("UTF-8");
	
	private String type;
	private CharactorType(String value)
	{
		type = value;
	}

	@Override
	public String toString() {
		return type;
	}
	
	
}
