package com.pmt.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.pmt.framework.util.context.ContextBeanUtil;

public class FrameworkFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO 自动生成的方法存根
		System.out.println("frameworkFilter init");
		//初始化库表
		ServletContext servletContext = config.getServletContext();
		ContextBeanUtil.getBean(servletContext, "userServiceImpl");
	}
	
	

}
