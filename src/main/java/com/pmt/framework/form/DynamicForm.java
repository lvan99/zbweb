/**   
* 文件名 : DynamicForm
* 包名 : com.ctg.qdp.controller.basePage
* 描述 : 动态Form
* 创建者 : liudw   
* 时间 : 2013-3-22 
* 版本 : V1.0
* 版权 :  2013 www.ctgpc.com.cn Inc. All rights reserved
*/
package com.pmt.framework.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DynamicForm{
	private int page = 0;
    private int limit = 0;
    
    private transient Map<Object, Object> map;
    private List<String> list;
    
    public DynamicForm()
    {      
      map = new HashMap<Object, Object>();
    }
    
	/**
	 * @return limit
	 */
	public int getLimit() {
		return limit;
	}


	/**
	 * @param limit 要设置的 limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}


	public int getPage() {
      return page;
    }
    public void setPage(int page) {
      this.page = page;
    }
    public Map<Object, Object> getMap() {
      return map;
    }
    public void setMap(Map<Object, Object> map) {
      this.map = map;
    }
    
    public void set(Object key, Object value)
    {
      map.put(key, value);
    }

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public Object get(Object key)
    {
      return map.get(key);
    }
    
    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public int getInt(Object key)
    {
      Object value = map.get(key);
      if (value instanceof Integer)
      {
        return ((Integer) value).intValue();
      }
      return 0;
    }

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public Integer getInteger(Object key)
    {
      Object value = map.get(key);
      if (value instanceof Integer)
      {
        return (Integer) value;
      }
      return null;
    }

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public double getDouble(Object key)
    {
      Object value = map.get(key);
      if (value instanceof Double)
      {
        return ((Double) value).doubleValue();
      }
      return 0d;
    }

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public float getFloat(Object key)
    {
      Object value = map.get(key);
      if (value instanceof Float)
      {
        return ((Float) value).floatValue();
      }
      return 0f;
    }
    

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public String getString(Object key)
    {
      Object value = map.get(key);
      if (value == null)
      {
        return null;
      }
      else if (value instanceof String)
      {
        return (String) value;
      }

      return null;
    }

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public long getLong(Object key)
    {
      Object value = map.get(key);
      if (value instanceof Long)
      {
        return ((Long) value).longValue();
      }
      return 0l;
    }

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    @SuppressWarnings("unchecked")
	public List<DynamicForm> getFormList(Object key)
    {
      Object value = map.get(key);
      if (value instanceof List)
      {
        return (List<DynamicForm>) value;
      }
      return null;
    }

    /**
     * 根据key值获取对应的Value值
     * 
     * @param key
     *            要获取的key值
     * @return 返回key对应的value值
     */
    public java.util.Date getDate(Object key)
    {
      Object value = map.get(key);
      if (value instanceof Date)
      {
        return (Date) value;
      }
      if (value == null)
        return null;
      return new java.util.Date(new Long(value.toString()));
    }

    /**
     * 根据key值获取默认的日期格式的Value值
     * @param key 要获取的key值
     * @return 根据key值获取默认的日期格式的Value值
     */
    public String getFmtDate(Object key)
    {
      return getFmtDate(key, "yyyy-MM-dd");
    }    

    /**
     * 根据key值获取默认的日期格式的Value值
     * @param key 要获取的key值
     * @param pattern 日期格式化的格式
     * @return 根据key值获取默认的日期格式的Value值
     */
    public String getFmtDate(Object key, String pattern)
    {
      Object value = map.get(key);
      if (value == null)
      {
        return "";
      }
      SimpleDateFormat f = new SimpleDateFormat(pattern);
      return f.format(value);
    }
    
    /**
     * 根据key删除元素
     * @param key
     */
    public void remove(Object key){
    	map.remove(key);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

}
