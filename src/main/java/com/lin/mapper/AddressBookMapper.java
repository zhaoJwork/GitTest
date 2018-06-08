package com.lin.mapper;

import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;

public interface AddressBookMapper {

	// 添加禁言
	Integer addBannedSay(AddressBanned addressBanned);

	// 修改禁言
	Integer updateBannedSay(AddressBanned addressBanned);

	// 添加收藏
	Integer addAddressCollection(AddressCollection addressCollection);

	// 修改收藏
	Integer updateAddressCollection(AddressCollection addressCollection);

}
