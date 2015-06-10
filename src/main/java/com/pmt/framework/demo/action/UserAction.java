//package com.pmt.framework.demo.action;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.pmt.framework.demo.service.UserServiceI;
//import com.pmt.framework.form.DynamicForm;
//import com.pmt.framework.util.ActionUtil;
//import com.pmt.framework.util.context.PlatformContext;
//
//@Controller
//public class UserAction {
//	@Autowired
//	private UserServiceI userServiceImpl;
//	
//	@RequestMapping(value = "/demo/users/query", method = RequestMethod.POST)
//	public void query(HttpServletRequest request,HttpServletResponse response){
//		DynamicForm dynamicForm = PlatformContext.getDynamicForm();
//		Map<String, Object> result = userServiceImpl.query(dynamicForm);
//		ActionUtil.writeResponse(result);
//	}
//}
