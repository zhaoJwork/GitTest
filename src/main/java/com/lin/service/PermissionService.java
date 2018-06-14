package com.lin.service;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.domain.AutoCollection;
import com.lin.domain.QAddressBanned;
import com.lin.domain.QAddressCollection;
import com.lin.domain.QPositionDsl;
import com.lin.domain.QUser;
import com.lin.domain.QUserNewAssistDsl;
import com.lin.domain.User;
import com.lin.repository.AddressBannedRepository;
import com.lin.repository.AddressCollectionRepository;
import com.lin.repository.UserRepository;
import com.lin.util.Result;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * 
 * 通讯录
 * @author liudongdong
 * @date 2018年6年6月
 *
 */
@Service
public class PermissionService extends AbstractService<AddressCollection,String>{

	@Autowired
	public PermissionService(AddressCollectionRepository addressCollectionRepository) {
		super(addressCollectionRepository);
	}
	
	private UserRepository userRepository;

	@Resource
	private AddressBannedRepository addressBannedRepository;
	
	@Resource
	private AddressCollectionRepository addressCollectionRepository;
	
	@Autowired
    private EntityManager entityManager;
	
	private JPAQueryFactory queryFactory;  
    
    @PostConstruct  
    public void init() {  
       queryFactory = new JPAQueryFactory(entityManager);  
    }
	
	/**
	 * 禁言权限查询
	 * @param loginId
	 * @param type
	 * @param userId
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月12日
	 */
	public void getBannedSayCheck(String loginId, Integer type, Result result) {
		// 根据type判断 要查询的是哪种权限  1为禁言 2为能力指数
		if(type.equals(1)) {
			// 查询是否有权限
			List<?> resultList = entityManager.createNativeQuery("select count(1) from appuser.address_user u where u.dep_id = '626' and u.user_id = ?")
					.setParameter(1, loginId)
					.getResultList();
			if("1".equals(resultList.get(0).toString())) {
				result.setRespCode("1");
				result.setRespDesc("正常返回数据");
				result.setRespMsg("1");
			}else {
				result.setRespCode("2");
				result.setRespDesc("没有禁言权限");
				result.setRespMsg("0");
			}
		}else {
			result.setRespCode("2");
			result.setRespDesc("禁言权限查询失败");
			result.setRespMsg("0");
		}
		
	}
	
	/**
	 * 能力指数权限查询
	 * @param loginId
	 * @param type
	 * @param userId
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月12日
	 */
	public void getAbilitycheck(String loginId, Integer type, String userId, Result result) {
		// 根据type判断 要查询的是哪种权限  1为禁言 2为能力指数
		if(type.equals(2)) {
			// 能力指数的查看  如果角色为员工则没有权限进行下钻  
			List<?> resultList = entityManager.createNativeQuery("select appuser.F_O_bannedsay(?,?) from dual")
					.setParameter(1, loginId).setParameter(2, userId)
					.getResultList();
			if("1".equals(resultList.get(0).toString())) {
				result.setRespCode("1");
				result.setRespDesc("正常返回数据");
				result.setRespMsg("1");
			}else {
				result.setRespCode("2");
				result.setRespDesc("没有指数查询权限");
				result.setRespMsg("0");
			}
		}else {
			result.setRespCode("2");
			result.setRespDesc("指数能力权限查询失败");
			result.setRespMsg("0");
		}
		
	}
	
	/**
	 * 当前人是否已经被禁言查询
	 * @param loginId
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月13日
	 */
	public void getIsBannedSay(String loginId, Result result) {
		QAddressBanned qAddressBanned = QAddressBanned.addressBanned;
		BooleanExpression eq = qAddressBanned.bannedSayLoginId.eq(Integer.parseInt(loginId));
		Optional<AddressBanned> findOne = addressBannedRepository.findOne(eq);
		if("Optional.empty".equals(findOne.toString())) {
			result.setRespCode("1");
			result.setRespDesc("该用户没有被禁言");
			result.setRespMsg("1");
		}else {
			result.setRespCode("2");
			result.setRespDesc("该用户已经被禁言");
			result.setRespMsg("0");
		}
	}

	
	/**
	 * 添加禁言
	 * @author liudongdong
	 * @throws ParseException 
	 * @date 2018年6月6号
	 */
	public void addBannedSay(AddressBanned addressBanned, Result result) throws ParseException {
		QAddressBanned qAddressBanned = QAddressBanned.addressBanned;
		BooleanExpression eq = qAddressBanned.bannedSayUserId.eq(addressBanned.getBannedSayUserId());
		Optional<AddressBanned> findOne = addressBannedRepository.findOne(eq);
		Calendar cal = Calendar.getInstance();
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
					result.setRespMsg("0");
					return;
				}
				// 判断是否已经被禁言  如果要禁言人  已经被禁言 则进行修改
				if("Optional.empty".equals(findOne.toString())) {
					// 禁言初始添加
					bannedInitialAdd(addressBanned, cal, result);
				}else {
					AddressBanned banned = findOne.get();
					Date date = cal.getTime();
					// 禁言自动解封和请求追加禁言
					autoBannedSay(addressBanned, cal, banned, date, result);
				}
			}else {
				result.setRespCode("2");
				result.setRespDesc("禁言操作失败");
				result.setRespMsg("0");
			}
			
		}else {
			// TODO 其它待做
			result.setRespCode("2");
			result.setRespDesc("待做");
			result.setRespMsg("0");
		}
		
	}
	


	/**
	 * 禁言初始添加
	 * @param addressBanned
	 * @param cal
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月12日
	 */
	private void bannedInitialAdd(AddressBanned addressBanned, Calendar cal, Result result) {
		// 创建日期
		addressBanned.setCreateDate(cal.getTime());
		// 截至日期
		cal.add(Calendar.DAY_OF_MONTH, addressBanned.getBannedSayDays());
		addressBanned.setBannedSayDate(cal.getTime());
		AddressBanned save = addressBannedRepository.save(addressBanned);
		if(save != null) {
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg("1");
		}else {
			result.setRespCode("2");
			result.setRespDesc("禁言添加失败");
			result.setRespMsg("0");
		}
		
	}

	/**
	 * 禁言自动解封和追加
	 * @param addressBanned
	 * @param cal
	 * @param banned
	 * @param date
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月12日
	 */
	private void autoBannedSay(AddressBanned addressBanned, Calendar cal, AddressBanned banned, Date date, Result result) {
		// 根据当前时间进行判断是否已经禁言失效   如果失效 则禁言自动解封
		if(date.getTime() >= banned.getBannedSayDate().getTime() || banned.getBannedSayType() == 2) {
			banned.setBannedSayDays(addressBanned.getBannedSayDays());
			
			cal.add(Calendar.DAY_OF_MONTH, addressBanned.getBannedSayDays());
			banned.setBannedSayDate(cal.getTime());
			
			banned.setUpdateDate(date);
			banned.setUpdateBy(addressBanned.getBannedSayLoginId());
			
			// 如果禁言 在已经解封的基础上继续禁言
			long time = cal.getTime().getTime();
			if(time > date.getTime()) {
				// 自动解封
				banned.setBannedSayType(addressBanned.getBannedSayType());
			}
			// 修改
			AddressBanned save = addressBannedRepository.save(banned);
			if(save != null) {
				result.setRespCode("1");
				result.setRespDesc("正常返回数据");
				result.setRespMsg("1");
			}else {
				result.setRespCode("2");
				result.setRespDesc("禁言修改失败");
				result.setRespMsg("0");
			}
		}else {
			result.setRespCode("2");
			result.setRespDesc("该用户已经被禁言");
			result.setRespMsg("0");
		}
		
	}

	/**
	 * 取消禁言
	 * @author liudongdong
	 * @throws ParseException 
	 * @date 2018年6月11号
	 */
	public void cancelBannedSay(AddressBanned addressBanned, Result result) throws ParseException {
		QAddressBanned qAddressBanned = QAddressBanned.addressBanned;
		BooleanExpression eq = qAddressBanned.bannedSayUserId.eq(addressBanned.getBannedSayUserId());
		Optional<AddressBanned> findOne = addressBannedRepository.findOne(eq);
		Calendar cal = Calendar.getInstance();
		// 1禁言 2其它
		if(addressBanned.getType().equals(1)) {
			
			if(addressBanned.getBannedSayType().equals(2)) {
				if("Optional.empty".equals(findOne.toString())) {
					result.setRespCode("2");
					result.setRespDesc("该用户无禁言记录，请先禁言！");
					result.setRespMsg("0");
				}else {
					AddressBanned banned = findOne.get();
					// 进行取消禁言
					updateAddressSay(addressBanned, banned, cal, result);
				}
			}else {
				result.setRespCode("2");
				result.setRespDesc("禁言操作失败");
				result.setRespMsg("0");
			}
			
		}else {
			// TODO 其它待做
			result.setRespCode("2");
			result.setRespDesc("待做");
			result.setRespMsg("0");
		}
		
	}
	
	/**
	 * 取消禁言
	 * @param addressBanned
	 * @param banned
	 * @param cal
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月12日
	 */
	private void updateAddressSay(AddressBanned addressBanned, AddressBanned banned, Calendar cal, Result result) {
		banned.setBannedSayType(addressBanned.getBannedSayType());
		// 修改日期
		banned.setUpdateDate(cal.getTime());
		// 修改人为当前登录人
		banned.setUpdateBy(addressBanned.getBannedSayLoginId());
		
		AddressBanned save = addressBannedRepository.save(banned);
		if(save != null) {
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg("1");
		}else {
			result.setRespCode("2");
			result.setRespDesc("禁言修改失败");
			result.setRespMsg("0");
		}
		
	}


	
	/**
	 * 添加收藏
	 * @author liudongdong
	 * @date 2018年6月7日
	 */
	public void addAddressCollection(AddressCollection addressCollection, Result result) {
		QAddressCollection qAddressCollection = QAddressCollection.addressCollection;
		
		Date date = Calendar.getInstance().getTime();
		// 1收藏 2其它 默认0
		if(addressCollection.getType().equals(1)) {
			
			// 1收藏  2取消收藏 0不处理
			if(addressCollection.getCollectionType().equals(1)) {
				BooleanExpression eq = qAddressCollection.collectionLoginId.eq(addressCollection.getCollectionLoginId())
						.and(qAddressCollection.collectionUserId.eq(addressCollection.getCollectionUserId()));
				Optional<AddressCollection> findOne = addressCollectionRepository.findOne(eq);
				if("Optional.empty".equals(findOne.toString())) {
					// 创建日期
					addressCollection.setCollectionCreateDate(date);
					
					AddressCollection save = addressCollectionRepository.save(addressCollection);
					if(save == null) {
						result.setRespCode("2");
						result.setRespDesc("收藏添加失败");
						result.setRespMsg("0");
					}else {
						result.setRespCode("1");
						result.setRespDesc("正常返回数据");
						result.setRespMsg("1");
					}
				}else {
					AddressCollection collection = findOne.get();
					if(collection.getCollectionType().equals(2)) {
						// 修改日期
						collection.setCollectionUpdateDate(date);
						// 修改人为当前登录人
						collection.setCollectionUpdateBy(addressCollection.getCollectionLoginId());
						collection.setCollectionType(addressCollection.getCollectionType());
						AddressCollection save = addressCollectionRepository.save(collection);
						if(save == null) {
							result.setRespCode("2");
							result.setRespDesc("收藏修改失败");
							result.setRespMsg("0");
						}else {
							result.setRespCode("1");
							result.setRespDesc("正常返回数据");
							result.setRespMsg("1");
						}
					}else {
						result.setRespCode("2");
						result.setRespDesc("用户已经处于收藏状态");
						result.setRespMsg("0");
					}
					
				}
				
				
			}else {
				result.setRespCode("2");
				result.setRespDesc("收藏操作失败");
				result.setRespMsg("0");
			}
			
		}else {
			// TODO 其它待做
			result.setRespCode("2");
			result.setRespDesc("待做");
			result.setRespMsg("0");
		}
		
	}
	
	/**
	 * 取消收藏
	 * @author liudongdong
	 * @date 2018年6月7日
	 */
	public void cancelAddressCollection(AddressCollection addressCollection, Result result) {
		
		Date date = Calendar.getInstance().getTime();
		// 1收藏 2其它 默认0
		if(addressCollection.getType().equals(1)) {
			
			// 1收藏  2取消收藏 0不处理
			if(addressCollection.getCollectionType().equals(2)) {
				QAddressCollection qAddressCollection = QAddressCollection.addressCollection;
				BooleanExpression eq = qAddressCollection.collectionLoginId.eq(addressCollection.getCollectionLoginId())
					.and(qAddressCollection.collectionUserId.eq(addressCollection.getCollectionUserId()));
				Optional<AddressCollection> findOne = addressCollectionRepository.findOne(eq);
				
				if("Optional.empty".equals(findOne.toString())) {
					result.setRespCode("2");
					result.setRespDesc("取消收藏用户，不处在收藏列");
					result.setRespMsg("0");
				}else {
					AddressCollection collection = findOne.get();
					// 修改日期
					collection.setCollectionUpdateDate(date);
					// 修改人为当前登录人
					collection.setCollectionUpdateBy(addressCollection.getCollectionLoginId());
					collection.setCollectionType(addressCollection.getCollectionType());
					
					AddressCollection save = addressCollectionRepository.save(collection);
					if(save == null) {
						result.setRespCode("2");
						result.setRespDesc("收藏修改失败");
						result.setRespMsg("0");
					}else {
						result.setRespCode("1");
						result.setRespDesc("正常返回数据");
						result.setRespMsg("1");
					}
				}
				
			}else {
				result.setRespCode("2");
				result.setRespDesc("收藏操作失败");
				result.setRespMsg("0");
			}
			
		}else {
			// TODO 其它待做
			result.setRespCode("2");
			result.setRespDesc("待做");
			result.setRespMsg("0");
		}
		
	}
	
	/**
	 * 当前人收藏列表查询
	 * @param loginId
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月13日
	 */
	public void getCollectionList(String loginId, Result result) {
		QUser qUser = QUser.user;
		QAddressCollection qAddressCollection = QAddressCollection.addressCollection;
		QUserNewAssistDsl uass = QUserNewAssistDsl.userNewAssistDsl;
		QPositionDsl qPositionDsl = QPositionDsl.positionDsl;
		
		List<AutoCollection> fetch = queryFactory.select(Projections.bean(AutoCollection.class,
				qAddressCollection.rowId,qAddressCollection.source,
				qUser.userID,qUser.userName,qUser.provinceID,qUser.phone,
				qUser.email,qUser.address,qUser.organizationID,
				new CaseBuilder()
					.when(uass.portrait_url.isNull())
					.then(qUser.userPic)
					.otherwise(uass.portrait_url).as("userPic"),
				qUser.type,qUser.context, qUser.field,qPositionDsl.posName,
				qUser.deptype,qUser.quanPin,qUser.shouZiMu,qUser.hytAccount,
				qUser.crmAccount,qUser.flagOnline,qUser.sortNum,
				qUser.orderNum,uass.install
				))
		.from(qAddressCollection)
		.leftJoin(qUser).on(qUser.userID.eq(qAddressCollection.collectionUserId.stringValue()))
		.leftJoin(qPositionDsl).on(qUser.post.eq(qPositionDsl.posId))
		.leftJoin(uass).on(uass.userid.eq(qUser.userID))
		.where(qAddressCollection.collectionLoginId.eq(Integer.parseInt(loginId))).fetch();
		
//		source 客户  cust_id
		for (AutoCollection autoCollection : fetch) {
			System.out.println(autoCollection);
		}
//		迪科接口 
//		list 返回收藏 
		
	}


	@Override
	public long deleteByIds(String... ids) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<AddressCollection> findByIds(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	


	
	
	
}
