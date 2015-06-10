/**
 * 
 */
package com.zoneber.account.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoneber.account.entity.ZbUserAccount;
import com.zoneber.account.service.ZbUserAccountServiceI;

/**
 * @author hu_hengjiu
 *
 */
@Controller
@RequestMapping(value = "/zoneber/account")
public class ZbUserAccountController {
	@Autowired
	private ZbUserAccountServiceI zbUserAccountService;
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public boolean login(ZbUserAccount userAccount, HttpServletRequest request, HttpServletResponse response) {
		List<ZbUserAccount> userAccountList = zbUserAccountService.selectForLogin(userAccount);
		if(userAccountList.isEmpty()) {
			//用户名或密码错误
			System.out.println(userAccount.getPassword()+"\t"+userAccount.getUserName());
			return false;
		} else {
			//获得帐号信息，存入Session
			ZbUserAccount loginUser = userAccountList.get(0);
			request.getSession().setAttribute("loginUser", loginUser);
			return true;
		}
	}
}
