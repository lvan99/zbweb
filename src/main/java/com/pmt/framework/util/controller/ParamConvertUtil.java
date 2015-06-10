/**   
* 文件名 : ParamConvertUtil.java 
* 包名 : com.ctg.qdp.util.controller 
* 描述 : 前端以JSON格式传递数据（含复杂数据对象），将请求参数解析工具类
* 创建者 : 胡恒久     
* 时间 : 2013-5-22 上午8:17:02 
* 版本 : V1.0
* 版权 :  2013 www.ctgpc.com.cn Inc. All rights reserved
*/
package com.pmt.framework.util.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.pmt.framework.constants.SystemConstants;

public class ParamConvertUtil {
	//private static Logger logger = LogFactory.getLog(ParamConvertUtil.class);
	
	/**
	 * 解析参数
	 * @param paramMap 从请求参数获得的参数集合对象
	 */
	public static Map<Object, Object> parse(Map<String, String[]> paramMap)
	{
		//Map<String, String[]> map = (Map<String, String[]>)request.getParameterMap();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();	//存储解析结果集合对象
		
		//logger.info("*************解析参数	开始************");
		//迭代请求参数键
		for(Iterator<String> it = paramMap.keySet().iterator(); it.hasNext();)
		{
			String key = it.next();	//参数键
			
			//排除分页关键字
			if(SystemConstants.PAGER_KEY_PAGE.equals(key) || SystemConstants.PAGER_KEY_ROWS.equals(key))
			{
				continue;
			}
			String[] values = (String[])paramMap.get(key);	//参数值，类型为字符串数组
			String value = values[0];
			
			//参数值为多个情况，将值以逗号拼接
			if(values.length > 1)
			{
				StringBuffer sb = new StringBuffer();
				for(String _value : values)
				{
					sb.append(_value).append(",");
				}
				
				//去掉最后一个逗号
				value = sb.substring(0, sb.length()-1);
			}
			
		///	logger.info(key+":"+value);
			//解析
			setValue(dataMap, key, value);
		}
		/*logger.info("dataMap:"+dataMap);
		logger.info("*************解析参数	结束************");*/
		return dataMap;
	}
	
	/**
	 * 设置值到数据对象中
	 * @param dataMap 存储数据对象
	 * @param key 请求的原始键名
	 * @param value 存储值
	 */
	@SuppressWarnings("unchecked")
	private static void setValue(Map<Object, Object> dataMap, String key, String value)
	{
		//简单键名
		if(key.indexOf("[") == -1)
		{
			dataMap.put(key, value);
		}
		else
		{
			//根据规则分析键名内容，获取最外围键名
			String base_key = key.substring(0, key.indexOf("["));
			
			//存储对象如果不含此键名，Null值占位
			if(!dataMap.containsKey(base_key))
			{
				dataMap.put(base_key, null);
			}
			
			Object lastObj = dataMap;	//上级存储对象
			String key_or_sub = base_key;	//上级存储的键名或者下标值
			
			/**
			 * 根据规则获得除外围键名外的键名数组，如rows[0][name],则获得["0","name"]
			 * */
			String array_key = key.substring(key.indexOf("["));
			String[] array_keys = array_key.substring(1, array_key.length()-1).split("]\\[");
			
			//循环键名数组
			for(int k = 0; k<array_keys.length; k++)
			{
				String sub_key = array_keys[k];
				
				//如果键名是无符号整型，则为数组对象
				if(isSignInteger(sub_key))
				{
					//上级存储的对象为Map
					if(lastObj instanceof Map)
					{
						//根据上级对象获取当前对象，如果无当前对象，则创建对象
						Object temp_obj = ((Map<String, Object>)lastObj).get(key_or_sub);
						if(temp_obj == null)
						{
							temp_obj = new ArrayList<Object>();
						}
						
						//下标
						int sub = "".equals(sub_key) ? 0 : Integer.parseInt(sub_key);
						
						//根据下标和List长度，动态Null值占位
						int list_size = ((List<Object>)temp_obj).size(); 
						if(list_size <= sub){
							for(int i=list_size; i< sub+1; i++)
							{
								((List<Object>)temp_obj).add(null);
							}
						}
						
						//最后一个循环对象为存值对象
						if(k == array_keys.length-1)
						{						
							((List<Object>)temp_obj).set(sub, value);
						}
						
						//当前存储到上级对象中，并将当前对象作为下一次循环的上级对象
						((Map<String, Object>)lastObj).put(key_or_sub, temp_obj);
						lastObj = ((List<Object>)temp_obj);
						key_or_sub = sub_key;
						continue;
					}
					
					if(lastObj instanceof List)
					{
						int sub = Integer.parseInt(sub_key);
						List<Object> temp_obj = (List<Object>)((List<Object>)lastObj).get(sub);
						if(temp_obj == null)
						{
							temp_obj = new ArrayList<Object>();
						}
						int list_size = temp_obj.size(); 
						if(list_size <= sub){
							for(int i=list_size; i< sub+1; i++)
							{
								temp_obj.add(null);
							}
						}
						if(k == array_keys.length-1)
						{								
							((List<Object>)temp_obj).set(sub, value);							
						}						
						
						lastObj = temp_obj;
						key_or_sub = sub_key;
					}
					
				}
				else	//如果是Map对象
				{
					
					if(lastObj instanceof List)
					{
						int sub = Integer.parseInt(key_or_sub);
						Map<String, Object> map = (Map<String, Object>)((List<Object>)lastObj).get(sub);
						if(map == null)
						{
							map = new HashMap<String, Object>();
						}
						if(k == array_keys.length-1)
						{								
							map.put(sub_key, value);								
						}
						else
						{
							if(!map.containsKey(sub_key))
								map.put(sub_key, null);
						}						
						
						((List<Object>)lastObj).set(sub, map);
						lastObj = map;
						key_or_sub = sub_key;
						continue;
					}
					
					if(lastObj instanceof Map)
					{
						Map<String, Object> temp_obj = (Map<String, Object>)((Map<String, Object>)lastObj).get(key_or_sub);
						if(temp_obj == null)
						{
							temp_obj = new HashMap<String, Object>();
						}
						if(k == array_keys.length-1)
						{								
							temp_obj.put(sub_key, value);								
						}
						else
						{
							temp_obj.put(sub_key, null);
						}	
						((Map<String, Object>)lastObj).put(key_or_sub, temp_obj);
						lastObj = temp_obj;
						key_or_sub = sub_key;
					}
				}
			}
		}
	}
	
	/**
	 * 判断字符串是否为无符号整型
	 * @param str 要判断的字符串
	 * @return 是否是无符号整型
	 */
	private static boolean isSignInteger(String str){
		return str.matches("^\\d?$");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		boolean is_number = isSignInteger("");
		System.out.println(is_number);
		
		
		System.out.println("isblank:"+StringUtils.isBlank(""));
	}

}
