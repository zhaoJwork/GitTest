package com.lin.service;

import javax.servlet.http.HttpServletRequest;

import com.lin.domain.AddressInfLogBean;
import com.lin.util.Result;

/**
 * @author lwz
 * @date 2017年8月20日
 */
public interface AddressInfLogServiceI {
	
	void saveAddressInfLog(AddressInfLogBean log,Result respJson);
	
	AddressInfLogBean getAddressInfLog(HttpServletRequest req ,String infName);
}
