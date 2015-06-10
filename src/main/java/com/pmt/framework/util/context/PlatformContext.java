/**   
* 文件名 : SessionContext.java 
* 包名 : com.ctg.qdp.util.context 
* 描述 : 平台上下文维护
* 创建者 : 胡恒久     
* 时间 : 2013-6-27 上午9:39:51 
* 版本 : V1.0
* 版权 :  2013 www.ctgpc.com.cn Inc. All rights reserved
*/
package com.pmt.framework.util.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pmt.framework.constants.SystemConstants;
import com.pmt.framework.form.DynamicForm;


public class PlatformContext {
	//请求线程
	private static ThreadLocal<HttpServletRequest> request_threadLocal = new ThreadLocal<HttpServletRequest>();
		
	//响应线程
	private static ThreadLocal<HttpServletResponse> reponse_threadLocal = new ThreadLocal<HttpServletResponse>();
		
	public static void setRequest(HttpServletRequest request)
	{
		request_threadLocal.set(request);
	}
	
	public static HttpServletRequest getRequest()
	{
		return request_threadLocal.get();
	}
	
	public static void removeRequest()
	{
		request_threadLocal.remove();
	}
	
	public static void setResponse(HttpServletResponse response)
	{
		reponse_threadLocal.set(response);
	}
	
	public static HttpServletResponse getResponse()
	{
		return reponse_threadLocal.get();
	}
	
	public static void removeResponse()
	{
		reponse_threadLocal.remove();
	}
		
	public static HttpSession getSession()
	{
		return ((HttpServletRequest)getRequest()).getSession();
	}
	
	
	/**
	 * 获得会话存储的对象
	 * @param key 键名
	 * @return
	 */
	public static Object getSessionAttribute(String key){
		HttpSession session = getSession();
		return session == null ? null : session.getAttribute(key);
	}
	
	/**
	 * 获得请求存储的对象
	 * @param key 键名
	 * @return
	 */
	public static Object getRequestAttribute(String key)
	{
		return request_threadLocal.get() == null ? null : request_threadLocal.get().getAttribute(key);
	}
	
	/**
	 * 获得当前线程中的DynamicForm对象
	 * @return
	 */
	public static DynamicForm getDynamicForm()
	{
		return (DynamicForm)getRequestAttribute(SystemConstants.DYNAMICFORM);
	}
}
