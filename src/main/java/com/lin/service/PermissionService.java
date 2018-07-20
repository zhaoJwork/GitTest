package com.lin.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;

import com.lin.domain.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideal.wheel.common.AbstractService;
import com.lin.repository.AddressBannedRepository;
import com.lin.repository.AddressColAuxiliaryRepository;
import com.lin.repository.AddressCollectionRepository;
import com.lin.util.Result;
import com.lin.util.XmlReqAndRes;
import com.lin.vo.AddressCollectionVo;
import com.lin.vo.AutoCollectionVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
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

	private static final String String = null;

	@Autowired
	public PermissionService(AddressCollectionRepository addressCollectionRepository) {
		super(addressCollectionRepository);
	}

	@Value("${application.ADDB_DK}")
	private String addressBookDKUrl;
	@Value("${application.CUST_IMG}")
	private String custIMG;
	@Value("${application.pic_HttpIP}")
	private String picHttpIp;

	@Resource
	private AddressBannedRepository addressBannedRepository;

	@Resource
	private AddressCollectionRepository addressCollectionRepository;

	@Resource
	private AddressColAuxiliaryRepository addressColAuxiliaryRepository;

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
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月12日
	 */
	public void getBannedSayCheck(String loginId, Integer type, Result result) {
		// 根据type判断 要查询的是哪种权限  1为禁言 2为能力指数
		if(type.equals(1)) {
			// 查询是否有权限
			QUserStaff qUserStaff = QUserStaff.userStaff;

			Long resultLong = queryFactory.select(
					qUserStaff.staffID.count()
			)
					.from(qUserStaff)
					.where(qUserStaff.departmentID.eq(626)
							.and(qUserStaff.staffID.eq(Integer.parseInt(loginId))))
					.fetchOne();

			if(resultLong > 0) {
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
		BooleanExpression eq = qAddressBanned.bannedSayUserId.eq(Integer.parseInt(loginId))
				.and(qAddressBanned.bannedSayType.eq(1)
						.and(qAddressBanned.type.eq(1)));
		Optional<AddressBanned> findOne = addressBannedRepository.findOne(eq);
		if("Optional.empty".equals(findOne.toString())) {
			result.setRespCode("1");
			result.setRespDesc("该用户没有被禁言");
			result.setRespMsg("1");
		}else {
			result.setRespCode("1");
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
	public void addAddressCollection(AddressCollectionVo collectionVo, Result result) {
		AddressCollection collection = new AddressCollection();
		collection.setCollectionLoginId(collectionVo.getCollectionLoginId());
		collection.setCollectionUserId(collectionVo.getCollectionUserId());
		collection.setType(collectionVo.getType());
		collection.setCollectionType(collectionVo.getCollectionType());
		collection.setSource(collectionVo.getSource());
		QAddressCollection qAddressCollection = QAddressCollection.addressCollection;
		QAddressColAuxiliary auxiliary = QAddressColAuxiliary.addressColAuxiliary;
		Date date = Calendar.getInstance().getTime();
		// 1收藏 2其它 默认0
		if(collection.getType().equals(1)) {

			// 1收藏  2取消收藏 0不处理
			if(collection.getCollectionType().equals(1)) {
				BooleanExpression eq = qAddressCollection.collectionLoginId.eq(collection.getCollectionLoginId())
						.and(qAddressCollection.collectionUserId.eq(collection.getCollectionUserId()));
				Optional<AddressCollection> findOne = addressCollectionRepository.findOne(eq);
				if("Optional.empty".equals(findOne.toString())) {
					// 创建日期
					collection.setCollectionCreateDate(date);
					collection.setRowId(getSeq());
					AddressCollection save = addressCollectionRepository.save(collection);
					collectionVo.setRowId(save.getRowId());
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
					AddressCollection collectionOne = findOne.get();
					if(collectionOne.getCollectionType().equals(2)) {
						// 修改日期
						collectionOne.setCollectionUpdateDate(date);
						// 修改人为当前登录人
						collectionOne.setCollectionUpdateBy(collection.getCollectionLoginId());
						collectionOne.setCollectionType(collection.getCollectionType());
						AddressCollection save = addressCollectionRepository.save(collectionOne);
						collectionVo.setRowId(save.getRowId());
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

				if("1".equals(result.getRespCode())){
					AddressColAuxiliary auxVo = new AddressColAuxiliary();
					auxVo.setRowId(collectionVo.getRowId());
					addressColAuxiliaryRepository.delete(auxVo);
					auxVo.setQuanPin(collectionVo.getQuanPin());
					auxVo.setShouZiMu(collectionVo.getShouZiMu());
					auxVo.setName(collectionVo.getName());
					auxVo.setMobile(collectionVo.getMobile());
					addressColAuxiliaryRepository.save(auxVo);
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
	 * 判断是否已经被收藏
	 * @param loginId
	 * @param contactId
	 * @return 1 已被收藏  0  未被收藏
	 */
	public String getIsCollection(String loginId,String contactId) {
		QAddressCollection qAddressCollection = QAddressCollection.addressCollection;
		AddressCollection collection = queryFactory.selectFrom(qAddressCollection)
				.where(qAddressCollection.collectionUserId.eq(Integer.parseInt(contactId))
						.and(qAddressCollection.collectionLoginId.eq(Integer.parseInt(loginId)))
						.and(qAddressCollection.collectionType.eq(1))
						.and(qAddressCollection.type.eq(1)))
				.fetchOne();
		if(collection != null) {
			return "1";
		}else {
			return "0";
		}
	}

	/**
	 * 当前人收藏列表查询
	 * @param loginId
	 * @param result
	 * @author liudongdong
	 * @date 2018年6月13日
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void getCollectionList(String loginId,String search, String pageSize, String pageNum, Result result) {
		List<AutoCollectionVo> resultList = new ArrayList<AutoCollectionVo>();
	// 条数
		String NUMBER = (pageNum == null || pageNum == "") ? "10000" : pageNum;
		//页数
		String PAGENUM = (pageSize == null || pageSize == "") ? "1" : pageSize;
		search = (search == null || search == "") ? "":search;
		QUser qUser = QUser.user;
		QAddressCollection qAddressCollection = QAddressCollection.addressCollection;
		QUserNewAssist uass = QUserNewAssist.userNewAssist;
		QPositionDsl qPositionDsl = QPositionDsl.positionDsl;
		QAddressColAuxiliary auxiliary = QAddressColAuxiliary.addressColAuxiliary;
		Predicate predicate =  auxiliary.name.like(search+"%")
				.or(auxiliary.quanPin.like(search+"%"))
				.or(auxiliary.mobile.like(search+"%"));
		List<AutoCollectionVo> fetch = queryFactory.select(Projections.bean(AutoCollectionVo.class,
				qAddressCollection.rowId,
				qAddressCollection.source,
				qUser.userID,
				qUser.userName,
				qUser.provinceID,
				qUser.phone,
				qUser.email,
				qUser.address,
				qUser.organizationID,
				new CaseBuilder().when(uass.portrait_url.eq("").or(uass.portrait_url.isNull())).then(qUser.userPic).otherwise(uass.portrait_url).as("userPic"),
				qPositionDsl.posName,
				qUser.deptype,
				qUser.quanPin,
				qUser.shouZiMu,
				qUser.hytAccount,
				qUser.crmAccount,
				qUser.flagOnline,
				uass.install,
				qAddressCollection.collectionUserId.as("colAuxContactID"),
				auxiliary.name.as("colAuxContactName"),
				auxiliary.mobile.as("colAuxContactMobile"),
				auxiliary.quanPin.as("colAuxQanPin"),
				auxiliary.shouZiMu.as("colAuxShouZiMu")
		))
				.from(qAddressCollection)
				.leftJoin(qUser).on(qUser.userID.eq(qAddressCollection.collectionUserId.stringValue()))
				.leftJoin(qPositionDsl).on(qUser.post.eq(qPositionDsl.posId))
				.leftJoin(uass).on(uass.userid.eq(qUser.userID))
				.leftJoin(auxiliary).on(auxiliary.rowId.eq(qAddressCollection.rowId))
				.where(qAddressCollection.collectionLoginId.eq(Integer.parseInt(loginId))
						.and(qAddressCollection.collectionType.eq(1)))
				.where(predicate)
				.orderBy(auxiliary.quanPin.asc())
//		.offset(Long.parseLong(PAGENUM)*(Long.parseLong(NUMBER)-1))
//		.limit(Long.parseLong(PAGENUM))
				.fetch();

		if(fetch == null || fetch.size() == 0) {
			result.setRespCode("1");
			result.setRespDesc("列表查询成功");
			result.setRespMsg(resultList);
			return;
		}
//		source 客户  cust_id
		String contactIDs = "";
		for (AutoCollectionVo autoCollection : fetch) {
			if(autoCollection.getSource() != null && 2 == autoCollection.getSource()){
				contactIDs += ","+autoCollection.getColAuxContactID();
			}
		}

		if(!"".equals(contactIDs)) {
			contactIDs = contactIDs.substring(1);
			//		迪科接口
			//		list 返回收藏
			//  获取部门
			QUserStaff qUserStaff = QUserStaff.userStaff;
			Integer deptId = queryFactory.select(qUserStaff.departmentID).from(qUserStaff)
					.where(qUserStaff.staffID.eq(Integer.parseInt(loginId))).fetchOne();

			//准备调用DK参数
			String busiCode = "CustOmer";

			String OLD_PARTY_CODE = "";// 客户编码
			String STATUS = "12";// 11模糊查询、12精确查询
			String ORDERBY = "1";// 降序


			Map<String,Object> map = new HashMap<>();
			map.put("OLD_PARTY_CODE", OLD_PARTY_CODE);
			map.put("STAFF_ID", loginId);
			map.put("DEPARTMENT_ID", deptId);

			// search匹配  是否至少一位
			if(search != "" && search.matches("[0-9]+")) {
				// 纯为数字  默认手机号
				map.put("CUSTCONTACT_NAME", "");
				map.put("MOBILE_PHONE", search.trim());
			}else if(search != "") {
				// 非纯数字 默认名称查询
				map.put("CUSTCONTACT_NAME", search.trim());
				map.put("MOBILE_PHONE", "");
			}else {
				// search 为空
				map.put("CUSTCONTACT_NAME", "");
				map.put("MOBILE_PHONE", "");
			}

			map.put("CONTACT_ID", contactIDs);
			map.put("STATUS", STATUS);
			map.put("NUMBER", NUMBER);
			map.put("PAGENUM", PAGENUM);
			map.put("ORDERBY", ORDERBY);

			// 获取远程数据
			Map<String, Object> xmlMap = XmlReqAndRes.reqAndRes(busiCode, addressBookDKUrl, map);

			if(xmlMap.isEmpty()) {
				List<AutoCollectionVo> localList = new ArrayList<AutoCollectionVo>();
				for(int i = 0; i < fetch.size(); i++) {
					if(fetch.get(i).getSource().equals(1)) {
						localList.add(fetch.get(i));
					}
				}
				result.setRespCode("1");
				result.setRespDesc("返回本地列表");
				result.setRespMsg(localList);
				return;
			}

			if(!"999".equals(((Map<?, ?>)xmlMap.get("TcpCont")).get("ResultCode"))) {

				Map<?, ?> mapp = ((Map<?, ?>)xmlMap.get("SvcCont"));
				Object object = mapp.get("ADD_CUST_CONTACTS");
				List<Map<?, ?>> list = null;
				if(object instanceof List) {
					list = (List<Map<?, ?>>)object;
				}else {
					list = new ArrayList<>();
					list.add((Map<?, ?>)object);
				}
				// 根据迪克数据封装返回值 以迪科为准
				for(int i = 0; i < fetch.size(); i++) {
					if (1 == fetch.get(i).getSource()) {
						resultList.add(fetch.get(i));
					} else {
						// 0 为正常不处理  1为修改  2为删除
						int flag = 0;
						for (int j = 0; j < list.size(); j++) {
							Map<?, ?> m = (Map<?, ?>) list.get(j);
							String CONTACTID = (String) m.get("CONTACT_ID");
							String oldPartyCode = (String)m.get("OLD_PARTY_CODE");
							String custImg = picHttpIp + "/1/mphotos/10000004.png";
							String  industry = (String)m.get("INDUSTRY");
							industry = industry.substring(0, 2);
							if (industry.equals("FF")) {//制造业
								custImg = custIMG + "zhizaonengyuan.png";
							}
							if (industry.equals("EE")) {//医疗卫生
								custImg = custIMG + "yiliaoweisheng.png";
							}
							if (industry.equals("DD")) {//金融业
								custImg = custIMG + "jinrongye.png";
							}
							if (industry.equals("CC")) {//交通物流
								custImg = custIMG + "jiaotongwuliu.png";
							}
							if (industry.equals("II")) {//现代农业
								custImg = custIMG + "xiandainongye.png";
							}
							if (industry.equals("HH")) {//商业客户
								custImg = custIMG + "shangyekehu.png";
							}
							if (industry.equals("BB")) {//服务业
								custImg = custIMG + "fuwuhangye.png";
							}
							if (industry.equals("GG")) {//教育行业
								custImg = custIMG + "jiaoyuhangye.png";
							}
							if (industry.equals("AA")) {//政务行业
								custImg = custIMG + "zhengwuhangye.png";
							}
							// 客户编码list  进行客户图片替换
							QCust qCust = QCust.cust;
							QCustTreeRel qCustTreeRel = QCustTreeRel.custTreeRel;
							QCustTreeNode qCustTreeNode = QCustTreeNode.custTreeNode;
							QDKLogourl qdkLogourl = QDKLogourl.dKLogourl;


							List<Tuple> fetchvo = queryFactory.select(
									qCust.oldPartyCode,
									qdkLogourl.twoxUrl
							)
									.from(qdkLogourl)
									.leftJoin(qCustTreeNode).on(qdkLogourl.custNodeCD.eq(qCustTreeNode.custNodeCD.stringValue()))
									.leftJoin(qCustTreeRel).on(qCustTreeNode.custNodeID.eq(qCustTreeRel.custNodeID))
									.leftJoin(qCust).on(qCust.custID.eq(qCustTreeRel.custID))
									.where(qCust.oldPartyCode.in(oldPartyCode))
									.fetch();

							if(fetchvo.size() > 0) {

								for (int jj = 0; jj < fetchvo.size(); jj++) {
									if (fetchvo.get(jj).get(qCust.oldPartyCode).equals(oldPartyCode)) {
										String imgUrl = fetchvo.get(jj).get(qdkLogourl.twoxUrl);
										custImg = custIMG + "/" + imgUrl.substring(34, imgUrl.length());
									}
								}

							}
							if (CONTACTID.equals(fetch.get(i).getColAuxContactID().toString())) {
								flag = 0;
								if (!((String) m.get("CONTACT_NAME")).equals(fetch.get(i).getUserName()) ||
										!((String) m.get("MOBILE_PHONE")).equals(fetch.get(i).getPhone())) {

									// 本地数据和迪科数据不一致  进行修改本地数据
									flag = 1;

									// 全拼  首字母  select f_get_hzpy('123张三sss')   from dual
									List<?> name = entityManager.createNativeQuery("select f_get_hzpy(?)   from dual")
											.setParameter(1, (String) m.get("CONTACT_NAME")).getResultList();
									List<Integer> fetch2 = queryFactory.select(qAddressCollection.rowId)
											.from(qAddressCollection)
											.where(qAddressCollection.collectionUserId.eq(fetch.get(i).getColAuxContactID()))
											.fetch();

									Number[] auxiNum = new Number[fetch2.size()];
									for (int n = 0; n < fetch2.size(); n++) {
										auxiNum[n] = (Number) fetch2.get(n);
									}

									// 修改   手机号变更更新appuser.ADDRESS_COLAUXILIARY ;
									String mobile = (String) m.get("MOBILE_PHONE");
									String contactName = (String) m.get("CONTACT_NAME");
									String quanPin = name.get(0).toString();
									String shouZiMu = name.get(0).toString().substring(0, 1).toUpperCase();
									queryFactory.update(auxiliary)
											.set(auxiliary.mobile, mobile)
											.set(auxiliary.name, contactName)
											.set(auxiliary.quanPin, quanPin)
											.set(auxiliary.shouZiMu, shouZiMu)
											.where(auxiliary.rowId.in(auxiNum))
											.execute();

									AutoCollectionVo vo = fetch.get(i);
									vo.setColAuxContactMobile(mobile);
									vo.setColAuxContactName(contactName);
									vo.setColAuxQanPin(quanPin);
									vo.setColAuxShouZiMu(shouZiMu);
									vo.setColAuxImg(custImg);
									resultList.add(vo);
								}
								AutoCollectionVo vo = fetch.get(i);
								vo.setColAuxImg(custImg);
								resultList.add(vo);
								break;
							} else {
								flag = 2;
							}
						}
						if (flag == 2) {
							// 删除   迪科  本地都有数据数据的情况下   数据进行和迪科数据同步
							autoDele(contactIDs, qAddressCollection, auxiliary);
						}
					}
				}
				result.setRespCode("1");
				result.setRespDesc("列表查询成功");
				result.setRespMsg(resultList);
			}else {
				// 迪科没有数据  本地有数据  进行本地数据客户删除
				String str = "";
				for (AutoCollectionVo autoCollection : fetch) {
					if(2 == autoCollection.getSource()){
						str += autoCollection.getColAuxContactID()+";";
					}else {
						resultList.add(autoCollection);
					}
				}
				if(str != "") {
					autoDele(str, qAddressCollection, auxiliary);
				}

				result.setRespCode("1");
				result.setRespDesc("列表查询成功");
				result.setRespMsg(resultList);
			}

		}else {
			// 本地没有客户数据  直接返回本地查询企业数据
			result.setRespCode("1");
			result.setRespDesc("列表查询成功");
			result.setRespMsg(fetch);
		}

	}



	// 本地迪科数据同步
	@Transactional
	public void autoDele(String contactIDs, QAddressCollection qAddressCollection, QAddressColAuxiliary auxiliary) {
		String[] contactId = contactIDs.substring(0, contactIDs.length()-1).split(";");
		List<Integer> list = new ArrayList<Integer>();
		for(int m = 0; m < contactId.length; m++) {
			list.add(Integer.parseInt(contactId[m]));
		}

		List<Integer> fetch2 = queryFactory.select(qAddressCollection.rowId)
				.from(qAddressCollection)
				.where(qAddressCollection.collectionUserId.in(list.toArray(new Integer[] {}))).fetch();


		queryFactory.delete(auxiliary)
				.where(auxiliary.rowId.in(fetch2.toArray(new Integer[]{})))
				.execute();

		queryFactory.delete(qAddressCollection)
				.where(qAddressCollection.collectionUserId.in(list.toArray(new Integer[] {})))
				.execute();
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



	private Integer getSeq(){
		List noTalk = entityManager.createNativeQuery("select appuser.seq_app_addresslist.nextval from dual")
				.getResultList();
		return Integer.parseInt(noTalk.get(0).toString());
	}







}
