package com.lin.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lin.domain.BlackUserList;
import com.lin.domain.QBlackUserList;
import com.lin.domain.QOrganizationDsl;
import com.lin.domain.QUser;
import com.lin.domain.QUserNewAssist;
import com.lin.domain.QUserStaff;
import com.lin.domain.QUserTicketBean;
import com.lin.domain.UserStaff;
import com.lin.domain.UserTicketBean;
import com.lin.repository.AddressBlackListRepository;
import com.lin.util.Result;
import com.lin.vo.OperationPlatform;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * 运营平台-service类
 * @author liudongdong
 * @date 2018年10月15日
 *
 */
@Service("operationPlatformService")
public class OperationPlatformService {

	@Resource
	private AddressBlackListRepository addressBlackListRepository;
	
	@Value("${application.pic_HttpIP}")
	private String picHttpIp ;
	
	@Autowired
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}
	
	/**
	 * 用户模糊查询列表
	 * @param operat 参数实体类
	 * @author liudongdong
	 * @throws ParseException 
	 * @date 2018年10月19日
	 */
	public void selectUserList(Result result, OperationPlatform operat) throws ParseException {
		
		// 导入Querydsl部分局部实例
		QUser user = QUser.user;
		QUserNewAssist uass = QUserNewAssist.userNewAssist;
		QUserStaff qUserStaff = QUserStaff.userStaff;
		QOrganizationDsl organ = QOrganizationDsl.organizationDsl;
		QBlackUserList qBlackUserList = QBlackUserList.blackUserList;
		QUserTicketBean qUserTicketBean = QUserTicketBean.userTicketBean;
		
		// 动态拼接where条件
		List<Predicate> predicate = new ArrayList<Predicate>();
		Predicate[] pre = new Predicate[predicate.size()];
		
		if(operat.getStaffName() != null && !("".equals(operat.getStaffName())) ) {
			predicate.add(user.userName.like( "%"+operat.getStaffName()+"%"));
		}
		if(operat.getCrmAccount() != null && !("".equals(operat.getCrmAccount()))) {
			predicate.add(user.crmAccount.eq(operat.getCrmAccount()));
		}
		if(operat.getTelNum() != null && !("".equals(operat.getTelNum()))) {
			predicate.add(user.phone.eq(operat.getTelNum()));
		}
		if(operat.getDeptID() != null && !("".equals(operat.getDeptID()))) {
			predicate.add(organ.organizationID.eq(operat.getDeptID()));
		}
		if(operat.getType() != null && !("".equals(operat.getType()))) {
			predicate.add(qUserStaff.statusCD.eq(operat.getType()));
		}
		
		// 查询
		List<OperationPlatform> list = queryFactory.select(Projections.bean(OperationPlatform.class,
				user.userID.as("staffID"),
				user.userName.as("staffName"),
				new CaseBuilder()
					.when(
							qBlackUserList.rowID.eq("")
							.or(qBlackUserList.rowID.isNull())
						)
					.then("0")
					.otherwise("1")
					.as("blackType"),
				new CaseBuilder()
					.when(
							uass.portrait_url.eq("")
							.or(uass.portrait_url.isNull())
						)
					.then(user.userPic)
					.otherwise(uass.portrait_url)
					.as("userImg"),
				user.crmAccount,
				user.phone.as("telNum"),
				user.address,
				user.updateDate,
				qUserStaff.statusCD,
				organ.organizationID.as("deptID"),
				organ.organizationName.as("deptName")
		)).from(user)
				.leftJoin(uass).on(user.userID.eq(uass.userid))
				.leftJoin(qUserStaff).on(user.userID.eq(qUserStaff.staffID.stringValue()))
				.leftJoin(organ).on(organ.organizationID.eq(user.organizationID))
				.leftJoin(qBlackUserList).on(qBlackUserList.userID.eq(user.userID))
				.where(predicate.toArray(pre))
				.offset((Long.parseLong(operat.getPageSize())-1)*Long.parseLong(operat.getPageNum()))
				.limit(Long.parseLong(operat.getPageNum()))
				.fetch();
		
		// 结果返回
		if(list.size() !=0 ) {
			
			// 处理返回图像数据
			List<UserTicketBean> userTicketBeans = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < list.size(); i++) {
				
				userTicketBeans = queryFactory.select(Projections.bean(UserTicketBean.class,
						qUserTicketBean.createDate.max().as("createDate")
				))
				.from(qUserTicketBean)
				.where(qUserTicketBean.userName.eq(list.get(i).getCrmAccount()))
				.fetch();
				if(userTicketBeans.size() > 0  && !(userTicketBeans.get(0)==null)) {
					list.get(i).setIsLogin("0");
				}else {
					list.get(i).setIsLogin("1");
				}
				list.get(i).setUpdateDate(format.format(format.parse(list.get(i).getUpdateDate())));
				list.get(i).setUserImg(picHttpIp + list.get(i).getUserImg());
			}
			result.setRespCode("1");
			result.setRespDesc("查询成功");
			result.setRespMsg(list);
		}else {
			result.setRespCode("2");
			result.setRespDesc("暂无数据");
			result.setRespMsg("");
		}
	}

	/**
	 * 在线用户查询列表
	 * @param operat 参数实体类
	 * @author liudongdong
	 * @date 2018年10月15日
	 */
	public void selectislogin(Result result, OperationPlatform operat) {
		
		// 导入Querydsl部分局部实例
		QUser user = QUser.user;
		QUserNewAssist uass = QUserNewAssist.userNewAssist;
		QUserStaff qUserStaff = QUserStaff.userStaff;
		QOrganizationDsl organ = QOrganizationDsl.organizationDsl;
		QUserTicketBean qUserTicketBean = QUserTicketBean.userTicketBean;
		// 动态拼接where条件
		List<Predicate> predicate = new ArrayList<Predicate>();
		Predicate[] pre = new Predicate[predicate.size()];
		predicate.add(user.crmAccount.isNotNull());
		if(operat.getDeptID() != null && !("".equals(operat.getDeptID()))) {
			predicate.add(organ.organizationID.eq(operat.getDeptID()));
		}
		
		// 查询
		List<OperationPlatform> list = queryFactory.select(Projections.bean(OperationPlatform.class,
				user.userID.as("staffID"),
				user.userName.as("staffName"),
				new CaseBuilder()
					.when(
							uass.portrait_url.eq("")
							.or(uass.portrait_url.isNull())
						)
					.then(user.userPic)
					.otherwise(uass.portrait_url)
					.as("userImg"),
				user.crmAccount,
				user.phone.as("telNum"),
				user.email,
				user.sex,
				qUserStaff.statusCD,
				organ.organizationID.as("deptID"),
				organ.organizationName.as("deptName")
		)).from(qUserTicketBean)
				.leftJoin(user).on(qUserTicketBean.userName.eq(user.crmAccount))
				.leftJoin(uass).on(user.userID.eq(uass.userid))
				.leftJoin(qUserStaff).on(user.userID.eq(qUserStaff.staffID.stringValue()))
				.leftJoin(organ).on(organ.organizationID.eq(user.organizationID))
				.where(predicate.toArray(pre))
				.offset((Long.parseLong(operat.getPageSize())-1)*Long.parseLong(operat.getPageNum()))
				.limit(Long.parseLong(operat.getPageNum()))
				.distinct()
				.fetch();
		
		// 返回判断结果
		if(list.size() !=0 ) {
			
			// 返回结果添加最近上线时间
			List<UserTicketBean> userTicketBeans = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < list.size(); i++) {
				userTicketBeans = queryFactory.select(Projections.bean(UserTicketBean.class,
						qUserTicketBean.createDate.max().as("createDate")
				))
				.from(qUserTicketBean)
				.where(qUserTicketBean.userName.eq(list.get(i).getCrmAccount()))
				.fetch();
				// 处理返回图像数据
				list.get(i).setUserImg(picHttpIp + list.get(i).getUserImg());
				
				if(userTicketBeans.size() > 0  && !(userTicketBeans.get(0)==null)) {
					list.get(i).setLoginTime(format.format(new Date(userTicketBeans.get(0).getCreateDate())));
				}
			}
			result.setRespCode("1");
			result.setRespDesc("查询成功");
			result.setRespMsg(list);
		}else {
			result.setRespCode("2");
			result.setRespDesc("暂无数据");
			result.setRespMsg("");
		}
		
	}

	/**
	 * 添加黑名单
	 * @param blackUserList 参数实体类
	 * @author liudongdong
	 * @date 2018年10月15日
	 */
	public void addblack(Result result, BlackUserList blackUserList) {
		QBlackUserList qBlackUserList = QBlackUserList.blackUserList;
		QUserStaff qUserStaff = QUserStaff.userStaff;
		Calendar cal = Calendar.getInstance();

		BlackUserList one = queryFactory.selectFrom(qBlackUserList)
			.where(qBlackUserList.userID.eq(blackUserList.getUserID()))
			.fetchOne();
		
		// 判断是否已经被加入黑名单  如果没有 则进行加入黑名单  否则进行返回提示
		if(one == null) {
			UserStaff staff = queryFactory.selectFrom(qUserStaff)
					.where(qUserStaff.staffID.eq(Integer.parseInt(blackUserList.getUserID())))
					.fetchOne();
			if(staff == null) {
				result.setRespCode("2");
				result.setRespDesc("该人员不在人员列表中!");
				result.setRespMsg("0");
			}else {
				blackUserList.setRowID(getSeq().toString());
				blackUserList.setCreateDate(cal.getTime());
				blackUserList.setUserName(staff.getStaffName());
				// 初始黑名单添加
				addressBlackListRepository.save(blackUserList);
				
				result.setRespCode("1");
				result.setRespDesc("添加成功");
				result.setRespMsg("1");
			}
		}else {
			result.setRespCode("2");
			result.setRespDesc("该人员已在黑名单中!");
			result.setRespMsg("0");
		}

	}

	/**
	 * 取消黑名单
	 * @param blackUserList 参数实体类
	 * @author liudongdong
	 * @date 2018年10月15日
	 */
	@Transactional
	public void cancelblack(Result result, BlackUserList blackUserList) {
		
		QBlackUserList qBlackUserList = QBlackUserList.blackUserList;
		
		// 条件拼接
		Predicate predicate =  qBlackUserList.userID.eq(blackUserList.getUserID())
				.and(qBlackUserList.createBy.eq(blackUserList.getCreateBy()));
		
		// 查询是否存在黑名单中
		BlackUserList one = queryFactory.selectFrom(qBlackUserList)
			.where(predicate)
			.fetchOne();
		
		// 判断是否已经被加入黑名单  如果有 则进行取消黑名单  否则进行返回提示
		if(one != null) {
			
			// 初始黑名单取消
			queryFactory.delete(qBlackUserList)
				.where(predicate)
				.execute();
			
			result.setRespCode("1");
			result.setRespDesc("取消成功");
			result.setRespMsg("1");
		}else {
			result.setRespCode("2");
			result.setRespDesc("该人员不在黑名单中!");
			result.setRespMsg("0");
		}
	}
	
	/**
	 * 获取序列
	 * @return
	 */
	private Integer getSeq(){
		List<?> noTalk = entityManager
				.createNativeQuery("select appuser.seq_app_addresslist.nextval from dual")
				.getResultList();
		return Integer.parseInt(noTalk.get(0).toString());
	}
}
