package com.pmt.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {
	public static String objToString(Object obj)
	{
		if(obj instanceof Boolean){
			return "{\"result\":"+obj+"}";
		}
		if(obj instanceof Map<?, ?>)
		{
			Map<?, ?> data = (Map<?, ?>)obj;
			JSONObject json = new JSONObject(data);
			return json.toString();
		}
		else if(obj instanceof List<?>)
		{
			List<?> data = (List<?>) obj;
			JSONArray json = new JSONArray(data);
			return json.toString();
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("id", 1);
		List list = new ArrayList();
		Map map1 = new HashMap();
		map1.put("age", 30);
		list.add(map1);
		map.put("name", "value");
		map.put("data", list);
		
		System.out.println(objToString(map));
		
		/*	JSONArray json1 = new JSONArray(list);
		System.out.println(json1.toString());*/
	}
}
