/**
 * 
 */
package com.zoneber.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoneber.account.dao.ZbUserAccountMapper;
import com.zoneber.account.entity.ZbUserAccount;
import com.zoneber.account.service.ZbUserAccountServiceI;

/**
 * @author hu_hengjiu
 *
 */
@Service
public class ZbUserAccountServiceImpl implements ZbUserAccountServiceI {
	@Autowired
	private ZbUserAccountMapper zbUserAccountMapper;
	
	public void insert() {
		
	}
	
	@Override
	public List<ZbUserAccount> selectForLogin(ZbUserAccount record) {
		List<ZbUserAccount> userAccountList = zbUserAccountMapper.selectForLogin(record);
		return userAccountList;
	}
}
