package com.lin.mapper;

import com.lin.domain.AddressInfLogBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志
 * @author lwz
 *
 */
@Mapper
public interface AddressInfLogMapper {
	
	void saveAddressInfLog(AddressInfLogBean log);
	
}
