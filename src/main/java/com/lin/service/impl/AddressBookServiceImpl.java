package com.lin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.mapper.AddressBookMapper;
import com.lin.service.AddressBookServiceI;
import com.lin.util.Result;

import sun.awt.datatransfer.DataTransferer.ReencodingInputStream;

/**
 * 
 * 通讯录
 * @author liudongdong
 * @date 2018年6年6月
 *
 */
@Service("addressBookService")
public class AddressBookServiceImpl implements AddressBookServiceI{

	
	@Autowired
	private AddressBookMapper addressDao;
	
	/**
	 * 查询禁言权限和能力指数 权限
	 * @author liudongdong
	 * @date 2018年6年6月
	 */
	@Override
	public void getBannedSay(String loginId, Integer type, String userId, Result result) {
		
		Integer re  = 0;
		// 根据type判断 要查询的是哪种权限  1为禁言 2为能力指数
		if(type.equals(1)) {
			// 权限的比较
			// 查询是否有权限
//				this.addressDao.get
				re = 0;
				// TODO
				if(re == 0) {
					result.setRespCode("2");
					result.setRespDesc("权限");
					result.setRespMsg(re);
				}else {
					result.setRespCode("1");
					result.setRespDesc("正常返回数据");
					result.setRespMsg(re);
				}
		}else if(type.equals(2)) {
			// 能力指数的查看  如果角色为员工则没有权限进行下钻 
//			this.addressDao.get
			// 权限的比较
			// TODO
			re = 1;
			if(re == 0) {
				result.setRespCode("2");
				result.setRespDesc("loginID 不能为空");
			}
		}
		
	}

	
	/**
	 * 添加禁言  如果rowid有值则进行修改
	 * @author liudongdong
	 * @date 2018年6月6号
	 */
	@Override
	public void addBannedSay(AddressBanned addressBanned, Result result) {
		Integer re = 0;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 1禁言 2其它
		if(addressBanned.getType().equals(1)) {
			
			// 1为禁言 2取消禁言 0不处理
			if(addressBanned.getBannedSayType().equals(1)) {
				if(addressBanned.getBannedSayDays()<0) {
					result.setRespCode("2");
					result.setRespDesc("bannedSayDays 值非法");
					result.setRespMsg(0);
				}
				// 创建日期
				addressBanned.setCreateDate(format.format(date));
				// 截至日期
				addressBanned.setBannedSayDate(format.format(date.getTime()+(24*60*60*1000)*addressBanned.getBannedSayDays()));
				
				re = this.addressDao.addBannedSay(addressBanned);
				if(re == 0) {
					result.setRespCode("2");
					result.setRespDesc("禁言添加失败");
					result.setRespMsg(0);
				}else {
					result.setRespCode("1");
					result.setRespDesc("正常返回数据");
					result.setRespMsg(1);
				}
				
			}else if(addressBanned.getBannedSayType().equals(2)) {
				// 修改日期
				addressBanned.setUpdateDate(format.format(date));
				// 修改人为当前登录人
				addressBanned.setUpdateBy(addressBanned.getBannedSayLoginId());
				
				re = this.addressDao.updateBannedSay(addressBanned);
				if(re == 0) {
					result.setRespCode("2");
					result.setRespDesc("禁言修改失败");
					result.setRespMsg(0);
				}else {
					result.setRespCode("1");
					result.setRespDesc("正常返回数据");
					result.setRespMsg(1);
				}
			}else {
				result.setRespCode("2");
				result.setRespDesc("禁言操作失败");
				result.setRespMsg(0);
			}
			
		}else {
			// TODO 其它待做
			result.setRespCode("2");
			result.setRespDesc("待做");
			result.setRespMsg(0);
		}
		
	}
	
	/**
	 * 添加或者取消收藏
	 * @author liudongdong
	 * @date 2018年6月7日
	 */
	@Override
	public void addressCollection(AddressCollection addressCollection, Result result) {
		Integer re = 1;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 1收藏 2其它 默认0
		if(addressCollection.getType().equals(1)) {
			
			// 1收藏  2取消收藏 0不处理
			if(addressCollection.getCollectionType().equals(1)) {
				// 创建日期
				addressCollection.setCollectionCreateDate(format.format(date));
				
				re = this.addressDao.addAddressCollection(addressCollection);
				if(re == 0) {
					result.setRespCode("2");
					result.setRespDesc("收藏添加失败");
					result.setRespMsg(0);
				}else {
					result.setRespCode("1");
					result.setRespDesc("正常返回数据");
					result.setRespMsg(1);
				}
				
			}else if(addressCollection.getCollectionType().equals(2)) {
				// 修改日期
				addressCollection.setCollectionUpdateDate(format.format(date));
				// 修改人为当前登录人
				addressCollection.setCollectionUpdateBy(addressCollection.getCollectionLoginId());
				
				re = this.addressDao.updateAddressCollection(addressCollection);
				if(re == 0) {
					result.setRespCode("2");
					result.setRespDesc("收藏修改失败");
					result.setRespMsg(0);
				}else {
					result.setRespCode("1");
					result.setRespDesc("正常返回数据");
					result.setRespMsg(1);
				}
			}else {
				result.setRespCode("2");
				result.setRespDesc("收藏操作失败");
				result.setRespMsg(0);
			}
			
		}else {
			// TODO 其它待做
			result.setRespCode("2");
			result.setRespDesc("待做");
			result.setRespMsg(0);
		}
		
		
	}
	
	
}
