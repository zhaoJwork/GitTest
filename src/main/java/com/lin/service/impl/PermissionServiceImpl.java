package com.lin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.domain.QAddressBanned;
import com.lin.mapper.PermissionMapper;
import com.lin.mapper.UtilMapper;
import com.lin.repository.AddressBannedRepository;
import com.lin.service.PermissionServiceI;
import com.lin.util.Result;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 * 
 * 通讯录
 * @author liudongdong
 * @date 2018年6年6月
 *
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionServiceI{

	
	@Autowired
	private PermissionMapper permissionDao;
	
	@Autowired
	private UtilMapper utilDao;
	
	@Resource
	private AddressBannedRepository addressBannedRepository;
	
	/**
	 * 查询禁言权限和能力指数 权限
	 * -- 能力指数  当前人 187207  查询人 121005  大于零有权限
        select appuser.F_O_bannedsay(187207,121005) from dual;
        
        -- 禁言权限 当前人 136109 大于零有权限
        select count(1) from appuser.address_user u where u.dep_id = '626' and u.user_id = '136109'
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
	 * @throws ParseException 
	 * @date 2018年6月6号
	 */
	@Override
	public void addBannedSay(AddressBanned addressBanned, Result result) throws ParseException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		QAddressBanned qAddressBanned = QAddressBanned.addressBanned;
		BooleanExpression eq = qAddressBanned.bannedSayUserId.eq(addressBanned.getBannedSayUserId());
		Optional<AddressBanned> findOne = addressBannedRepository.findOne(eq);
		// 1禁言 2其它
		if(addressBanned.getType().equals(1)) {
			
			// 1为禁言 2取消禁言 0不处理
			if(addressBanned.getBannedSayType().equals(1)) {
				if(addressBanned.getBannedSayDays() == null) {
					// 默认禁言31天
					addressBanned.setBannedSayDays(31);
				}else if(addressBanned.getBannedSayDays()<0) {
					result.setRespCode("2");
					result.setRespDesc("bannedSayDays 值非法");
					result.setRespMsg(0);
				}
				
				
				// 判断是否已经被禁言  如果要禁言人  已经被禁言 则进行修改
				if(findOne.toString().equals("Optional.empty")) {
					// 创建日期
					addressBanned.setCreateDate(format.parse(format.format(date)));
					// 截至日期
					addressBanned.setBannedSayDate(format.parse(format.format(date.getTime()+(24*60*60*1000)*addressBanned.getBannedSayDays())));
					addressBanned.setRowId(Integer.parseInt(utilDao.getSeqAppAddresslist()));
					AddressBanned save = addressBannedRepository.save(addressBanned);
					if(save != null) {
						result.setRespCode("1");
						result.setRespDesc("正常返回数据");
						result.setRespMsg(1);
					}else {
						result.setRespCode("2");
						result.setRespDesc("禁言添加失败");
						result.setRespMsg(0);
					}
					
				}else {
					// 根据当前时间进行判断是否已经禁言失效   如果失效 则禁言自动解封
					if(date.getTime()>=findOne.get().getBannedSayDate().getTime() || findOne.get().getBannedSayType() == 2) {
						AddressBanned banned = findOne.get();
						addressBanned.setRowId(banned.getRowId());
						addressBanned.setBannedSayDays(addressBanned.getBannedSayDays());
						addressBanned.setBannedSayDate(format.parse(format.format(date.getTime()+(24*60*60*1000)*addressBanned.getBannedSayDays())));
						addressBanned.setCreateDate(banned.getCreateDate());
						addressBanned.setUpdateDate(format.parse(format.format(date)));
						addressBanned.setUpdateBy(addressBanned.getBannedSayLoginId());
						
						// 如果禁言 在已经解封的基础上继续禁言
						long time = format.parse(format.format(date.getTime()+(24*60*60*1000)*addressBanned.getBannedSayDays())).getTime();
						if(time<=date.getTime()) {
							// 自动解封
							addressBanned.setBannedSayType(2);
						}
						
						AddressBanned save = addressBannedRepository.save(addressBanned);
						if(save != null) {
							result.setRespCode("1");
							result.setRespDesc("正常返回数据");
							result.setRespMsg(1);
						}else {
							result.setRespCode("2");
							result.setRespDesc("禁言修改失败");
							result.setRespMsg(0);
						}
					}else {
						result.setRespCode("2");
						result.setRespDesc("该用户已经被禁言");
						result.setRespMsg(0);
					}
				}
				
			}else if(addressBanned.getBannedSayType().equals(2)) {
				if(findOne.toString().equals("Optional.empty")) {
					result.setRespCode("2");
					result.setRespDesc("该用户无禁言记录，请先禁言！");
					result.setRespMsg(0);
				}else {
					// 修改日期
					addressBanned.setUpdateDate(format.parse(format.format(date)));
					// 修改人为当前登录人
					addressBanned.setUpdateBy(addressBanned.getBannedSayLoginId());
					
					AddressBanned banned = findOne.get();
					addressBanned.setRowId(banned.getRowId());
					addressBanned.setBannedSayDays(banned.getBannedSayDays());
					addressBanned.setBannedSayDate(banned.getBannedSayDate());
					addressBanned.setCreateDate(banned.getCreateDate());
					
					AddressBanned save = addressBannedRepository.save(addressBanned);
					if(save != null) {
						result.setRespCode("1");
						result.setRespDesc("正常返回数据");
						result.setRespMsg(1);
					}else {
						result.setRespCode("2");
						result.setRespDesc("禁言修改失败");
						result.setRespMsg(0);
					}
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
				
				re = this.permissionDao.addAddressCollection(addressCollection);
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
				
				re = this.permissionDao.updateAddressCollection(addressCollection);
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
