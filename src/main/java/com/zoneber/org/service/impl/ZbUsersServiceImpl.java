/**
 * 
 */
package com.zoneber.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoneber.org.dao.ZbUsersMapper;
import com.zoneber.org.entity.ZbUsers;
import com.zoneber.org.service.ZbUsersServiceI;

/**
 * @author hu_hengjiu
 *
 */
@Service
public class ZbUsersServiceImpl implements ZbUsersServiceI{
	@Autowired
	private ZbUsersMapper zbUserMapper;
	
	@Override
	public boolean insert(ZbUsers record) {
		return zbUserMapper.insertSelective(record) != -1;
	}
	
	@Override
	public boolean updateByPrimaryKey(ZbUsers record) {
		return zbUserMapper.updateByPrimaryKeySelective(record) != -1;
	}
	
	@Override
	public boolean deleteByPrimaryKey(String id) {
		return zbUserMapper.deleteByPrimaryKey(id) != -1;
	}
	
	@Override
	public List<ZbUsers> select() {
		return zbUserMapper.select();
	}
}
