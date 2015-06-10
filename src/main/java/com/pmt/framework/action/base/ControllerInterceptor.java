/**   
* 文件名 : ControllerInterceptor.java 
* 包名 : com.ctg.qdp.controller 
* 描述 : 控制拦截器
* 创建者 : liudw     
* 时间 : 2013-3-26 下午3:54:08 
* 版本 : V1.0
* 版权 :  2013 www.ctgpc.com.cn Inc. All rights reserved
*/
package com.pmt.framework.action.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pmt.framework.constants.SystemConstants;
import com.pmt.framework.enumeration.CharactorType;
import com.pmt.framework.form.DynamicForm;
import com.pmt.framework.util.context.PlatformContext;
import com.pmt.framework.util.controller.ParamConvertUtil;

public class ControllerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*//设置字符集编码
		response.setCharacterEncoding(CharactorType.UTF_8.toString());
		
		//默认返回test/xml,处理火狐解析错误问题。
		response.setContentType(SystemConstants.RESPONSE_CONTENT);*/
		
		//删除线程请求、响应对象
		PlatformContext.removeRequest();
		PlatformContext.removeResponse();
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/*****************************update by hu_hengjiu 2013.5.22**********************************/
		//设置请求字符集
		request.setCharacterEncoding(CharactorType.UTF_8.toString());
		
		//处理分页参数
		String page_str = request.getParameter(SystemConstants.PAGER_KEY_PAGE);
		String rows_str = request.getParameter(SystemConstants.PAGER_KEY_ROWS);
		
		int page = StringUtils.isBlank(page_str) ? 0 : Integer.parseInt(page_str);
		int rows = StringUtils.isBlank(rows_str) ? 0 : Integer.parseInt(rows_str);
		
		DynamicForm dynamicForm = new DynamicForm();
		
		//只有当传递的分页参数都不为0时才需要更新动态表单对应属性，否则为默认值0
		if(page != 0 && rows != 0)
		{
			dynamicForm.setPage(page);
			dynamicForm.setLimit(rows);
		}
		
		//获得请求参数Map并且解析
		Map<String, String[]> paramMap = (Map<String, String[]>)request.getParameterMap();
		Map<Object, Object> map = ParamConvertUtil.parse(paramMap);
		dynamicForm.setMap(map);
		/*******************************update by hu_hengjiu 2013.5.22********************************/
		//将当前操作用户名写入动态form供日志处理类使用 walker 2013-5-21
		//dynamicForm.set("currentUser", request.getSession().getAttribute("currentUser"));
		request.setAttribute(SystemConstants.DYNAMICFORM, dynamicForm);
		PlatformContext.setRequest(request);
		PlatformContext.setResponse(response);
		return super.preHandle(request, response, handler);
	}

}
