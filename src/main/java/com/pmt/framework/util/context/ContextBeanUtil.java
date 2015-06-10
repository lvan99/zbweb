package com.pmt.framework.util.context;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextBeanUtil {
	/**
	 * 获取应用程序上下文对象
	 * @param servletContext servlet上下文
	 * @return 应用程序上下文对象
	 */
	public static ApplicationContext getAppContext(ServletContext servletContext) {
		if(servletContext == null){
			return null;
		}
		ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext( servletContext );
		return appContext;
	}
	
	public static Object getBean(ServletContext servletContext, String beanName) {
		ApplicationContext appContext = getAppContext(servletContext);
		return appContext.getBean( beanName );
	}
}
