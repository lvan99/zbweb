package com.pmt.framework.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @Override
	public void init(ServletConfig config) throws ServletException {
    	initMemData( config );
		
    }
    
    /**
     * //初始化内存数据库
     * @param config
     */
    private void initMemData (ServletConfig config) {
//    	ApplicationContext ctx = 	WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
//		System.out.println("ctx:"+ctx);
//			//
//		UserServiceI user_service = (UserServiceI)ctx.getBean("userServiceImpl");
//		user_service.initData();
    }
    
    

}
