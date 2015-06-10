package com.pmt.framework.util.generator;

import java.util.UUID;

/**
 * 字段生成策略
 * @author Administrator
 *
 */
public class FieldGenerator {
	/**
	 * UUID获取
	 * @return UUID 32bit
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	
}
