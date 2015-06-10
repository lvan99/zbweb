/**
 * 
 */
package com.zoneber.org.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoneber.constants.ZoneberConstants;
import com.zoneber.org.entity.ZbUsers;
import com.zoneber.org.service.ZbUsersServiceI;

/**
 * @author hu_hengjiu
 *
 */
@Controller
@RequestMapping(value = "/zoneber/org/users")
public class ZbUsersController {
	@Autowired
	private ZbUsersServiceI zbUsersService;
	
//	@RequestMapping(value = "/insert")
//	@ResponseBody
//	public boolean insert(String oper, ZbUsers record) {
//		return zbUsersService.insert(record);
//	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public boolean update(String oper, ZbUsers record) {
		System.out.println("oper:"+oper);
		if(oper.equalsIgnoreCase(ZoneberConstants.OPER_ADD)) {
			return zbUsersService.insert(record);
		} else if(oper.equalsIgnoreCase(ZoneberConstants.OPER_UPDATE)) {
			return zbUsersService.updateByPrimaryKey(record);
		} else {
			//操作不对
			return false;
		}
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public boolean delete(String id) {
		return zbUsersService.deleteByPrimaryKey(id);
	}
	
	@RequestMapping(value = "/select")
	@ResponseBody
	public List<ZbUsers> select() {
		return zbUsersService.select();
	}
	
	
}
