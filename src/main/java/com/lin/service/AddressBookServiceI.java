package com.lin.service;

import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.util.Result;

/**
 * 
 * @author liudongdong
 * @date 2018年6月7日
 *
 */
public interface AddressBookServiceI {

	// 禁言 能力指数查看权限
	void getBannedSay(String loginId, Integer type, String userId, Result result);

	// 添加或者取消禁言
	void addBannedSay(AddressBanned addressBanned, Result result);

	// 添加或者取消收藏
	void addressCollection(AddressCollection addressCollection, Result result);

}
