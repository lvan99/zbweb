package com.pmt.framework.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.pmt.framework.constants.SystemConstants;
import com.pmt.framework.util.context.PlatformContext;

public class ActionUtil {
	public static void setResponseContent(HttpServletResponse response){
		response.setCharacterEncoding(SystemConstants.UTF_8);
		response.setContentType("text/plain,charset=UTF-8");
	}
	
	public static void writeResponse(Object result)
	{
		try {
			HttpServletResponse response = PlatformContext.getResponse();
			setResponseContent(response);
			response.getWriter().write(JSONUtil.objToString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
