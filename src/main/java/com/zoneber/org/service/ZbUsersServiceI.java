/**
 * 
 */
package com.zoneber.org.service;

import java.util.List;

import com.zoneber.org.entity.ZbUsers;

/**
 * @author hu_hengjiu
 *
 */
public interface ZbUsersServiceI {

	boolean insert(ZbUsers record);

	boolean updateByPrimaryKey(ZbUsers record);

	boolean deleteByPrimaryKey(String id);
	
	List<ZbUsers> select();

}
