/**
 * 
 */
package com.zoneber.account.service;

import java.util.List;

import com.zoneber.account.entity.ZbUserAccount;

/**
 * @author hu_hengjiu
 *
 */
public interface ZbUserAccountServiceI {
	List<ZbUserAccount> selectForLogin(ZbUserAccount record);
}
