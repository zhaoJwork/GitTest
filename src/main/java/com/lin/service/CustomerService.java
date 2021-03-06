package com.lin.service;

import com.lin.domain.*;
import com.lin.repository.AddressCollectionRepository;
import com.lin.util.Result;
import com.lin.util.XmlReqAndRes;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 客户通讯录
 * @author liudongdong
 * @date 2018年6月27日
 *
 */
@Service
public class CustomerService {

	@Value("${application.ADDB_DK}")
	private String addressBookDKUrl;

	@Value("${application.CUST_IMG}")
	private String custIMG;

	@Value("${application.pic_HttpIP}")
	private String picHttpIp;



	@Resource
	private AddressCollectionRepository addressCollectionRepository;

	@Autowired
	private PermissionService permission;


	@Autowired
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}

	/**
	 * 获取人员列表
	 * @param loginId
	 * @param search
	 * @param custID
	 * @param pageSize
	 * @param pageNum
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public void getContactList(String loginId, String search, String custID, String pageSize, String pageNum,
							   Result result) {
		// 获取部门
		QUserStaff qUserStaff = QUserStaff.userStaff;
		Integer deptId = queryFactory.select(qUserStaff.departmentID).from(qUserStaff)
				.where(qUserStaff.staffID.eq(Integer.parseInt(loginId))).fetchOne();

		//准备调用DK参数
		String busiCode = "CustOmer";
		custID = (custID == null || custID == "") ? "":custID;
		String OLD_PARTY_CODE = custID;// 客户编码+shoujih
		String STATUS = custID == "" || custID == null ? "11" : "12";// 11模糊查询、12精确查询
		String NUMBER = (pageNum == null || pageNum == "") ? "20" : pageNum; // 页数
		String PAGENUM = (pageSize == null || pageSize == "") ? "1" : pageSize; //条数
		String ORDERBY = "1";// 降序
		search = (search == null || search == "") ? "":search;

		Map<String,Object> map = new HashMap<>();
		map.put("OLD_PARTY_CODE", OLD_PARTY_CODE.trim());
		map.put("STAFF_ID", loginId);
		map.put("DEPARTMENT_ID", deptId);
		map.put("CONTACT_ID", "");

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

		map.put("STATUS", STATUS);
		map.put("NUMBER", NUMBER);
		map.put("PAGENUM", PAGENUM);
		map.put("ORDERBY", ORDERBY);

		Map<String, Object> xmlMap = XmlReqAndRes.reqAndRes(busiCode, addressBookDKUrl, map);
		if(xmlMap.isEmpty()) {
			result.setRespCode("2");
			result.setRespDesc("接口访问异常");
			result.setRespMsg("");
			return;
		}
		// 解析xml
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> resultMap;
		// 组装查询in条件
		List<String> tempList = new ArrayList<>();
		List<String> list2 = new ArrayList<String>();
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

			for(int i = 0; i < list.size(); i++) {
				resultMap = new HashMap<>();
				Map<?, ?> m = (Map<?, ?>)list.get(i);
				String contactId = (String)m.get("CONTACT_ID");
				String contactName = (String)m.get("CONTACT_NAME");
				//// 编码
				resultMap.put("contactID", contactId);
				//// 联系人
				resultMap.put("contactName", contactName);
				//// 联系人手机号
				resultMap.put("contactMobile", m.get("MOBILE_PHONE"));
				//// 联系人Email
				resultMap.put("contactEmail", m.get("E_MAIL"));
				//// 联系人所属部门
				resultMap.put("contactDept", (String)m.get("CUST_NAME")+ (String)m.get("DEPT_NAME"));
				//// 联系人职位名称
				resultMap.put("contactPOST", m.get("POST_NAME"));

				String oldPartyCode = (String)m.get("OLD_PARTY_CODE");
				// 行业编码
				tempList.add((String)m.get("INDUSTRY"));
				// 图片地址  先置空  后处理
				resultMap.put("img", "");

				// 该联系人是否收藏    true 已被收藏    false未被收藏
				resultMap.put("collection", permission.getIsCollection(loginId,contactId));
				// 全拼  首字母  select f_get_hzpy('123张三sss')   from dual
				List<?> name = entityManager.createNativeQuery("select f_get_hzpy(?)   from dual")
						.setParameter(1, contactName).getResultList();
				resultMap.put("quanPin", name.get(0).toString());
				resultMap.put("shouZiMu", name.get(0).toString().substring(0,1).toUpperCase());

				resultList.add(resultMap);
				if(oldPartyCode != null && oldPartyCode != "") {
					list2.add(oldPartyCode);
				}
			}


			// img处理   进行行业图片替换
			for(int i = 0; i < tempList.size(); i++) {
				String tempStr = tempList.get(i);
				Map<String, Object> imgMap = resultList.get(i);
				if(tempStr == null || tempStr == "") {
					imgMap.put("img", picHttpIp + "/1/mphotos/10000004.png");
					resultList.set(i, imgMap);
					continue;
				}

				String industry = tempStr.substring(0, 2);
				if (industry.equals("FF")) {//制造业
					imgMap.put("img", custIMG + "zhizaonengyuan.png");
				}
				if (industry.equals("EE")) {//医疗卫生
					imgMap.put("img", custIMG + "yiliaoweisheng.png");
				}
				if (industry.equals("DD")) {//金融业
					imgMap.put("img", custIMG + "jinrongye.png");
				}
				if (industry.equals("CC")) {//交通物流
					imgMap.put("img", custIMG + "jiaotongwuliu.png");
				}
				if (industry.equals("II")) {//现代农业
					imgMap.put("img", custIMG + "xiandainongye.png");
				}
				if (industry.equals("HH")) {//商业客户
					imgMap.put("img", custIMG + "shangyekehu.png");
				}
				if (industry.equals("BB")) {//服务业
					imgMap.put("img", custIMG + "fuwuhangye.png");
				}
				if (industry.equals("GG")) {//教育行业
					imgMap.put("img", custIMG + "jiaoyuhangye.png");
				}
				if (industry.equals("AA")) {//政务行业
					imgMap.put("img", custIMG + "zhengwuhangye.png");
				}
				resultList.set(i, imgMap);
			}


			String[] str = new  String[list2.size()];
			for (int i = 0; i < list2.size(); i++) {
				str[i]=list2.get(i);
			}


			// 客户编码list  进行客户图片替换
			QCust qCust = QCust.cust;
			QCustTreeRel qCustTreeRel = QCustTreeRel.custTreeRel;
			QCustTreeNode qCustTreeNode = QCustTreeNode.custTreeNode;
			QDKLogourl qdkLogourl = QDKLogourl.dKLogourl;


			List<Tuple> fetch = queryFactory.select(
					qCust.oldPartyCode,
					qdkLogourl.twoxUrl
			)
					.from(qCust)
					.leftJoin(qCustTreeRel).on(qCust.custID.eq(qCustTreeRel.custID))
					.leftJoin(qCustTreeNode).on(qCustTreeNode.custNodeID.eq(qCustTreeRel.custNodeID))
					.leftJoin(qdkLogourl).on(qdkLogourl.custNodeCD.eq(qCustTreeNode.custNodeCD.stringValue()))
					.where(qCust.oldPartyCode.in(str))
					.fetch();

			if(fetch.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					for (int j = 0 ; j < fetch.size() ; j ++) {
						if (fetch.get(j).get(qCust.oldPartyCode).equals(resultList.get(i).get("oldPartyCode"))) {
							String imgUrl = fetch.get(j).get(qdkLogourl.twoxUrl);
							resultList.get(i).put("img", custIMG + "/" + imgUrl.substring(34, imgUrl.length()));
						}
					}
				}
			}


		}
		result.setRespCode("1");
		result.setRespDesc("客户列表查询成功");
		result.setRespMsg(resultList);

	}


	/**
	 * 客户列表
	 * @param loginId
	 * @param search
	 * @param pageSize
	 * @param pageNum
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public void getCustList(String loginId, String search, String pageSize, String pageNum, Result result) {
		//准备调用DK参数
		String busiCode = "CustOmer";

		// 获取部门
		QUserStaff qUserStaff = QUserStaff.userStaff;
		Integer deptId = queryFactory.select(qUserStaff.departmentID).from(qUserStaff)
				.where(qUserStaff.staffID.eq(Integer.parseInt(loginId))).fetchOne();

		String STATUS = "10";
		String NUMBER = (pageNum == null || pageNum == "") ? "20" : pageNum;
		String PAGENUM = (pageSize == null || pageSize == "") ? "1" : pageSize;
		String ORDERBY = "1";
		search = (search == null || search == "") ? "":search;

		Map<String,Object> map = new HashMap<>();
		map.put("CUST_NAME", search.trim());
		map.put("STAFF_ID", loginId);
		map.put("DEPARTMENT_ID", deptId);
		map.put("STATUS", STATUS);
		map.put("NUMBER", NUMBER);
		map.put("PAGENUM", PAGENUM);
		map.put("ORDERBY", ORDERBY);

		Map<String, Object> xmlMap = XmlReqAndRes.reqAndRes(busiCode, addressBookDKUrl, map);

		if(xmlMap.isEmpty()) {
			result.setRespCode("2");
			result.setRespDesc("接口返回异常");
			result.setRespMsg("");
			return;
		}

		List<Object> resultList = new ArrayList<>();
		if(!"999".equals(((Map<?, ?>)xmlMap.get("TcpCont")).get("ResultCode"))) {

			Map<?, ?> mapp = ((Map<?, ?>)xmlMap.get("SvcCont"));
			Object object = mapp.get("ADD_CUST_NUM");
			List<Map<?, ?>> list = null;
			if(object instanceof List) {
				list = (List<Map<?, ?>>)object;
			}else {
				list = new ArrayList<>();
				list.add((Map<?, ?>)object);
			}

			Map<String, Object> resultMap = null;
			if(null == list || list.size() > 0) {
				// 从返回的数据中  解析出  客户编码  客户名称  数量
				for (int i = 0; i < list.size(); i++) {
					resultMap = new HashMap<>();
					Map<?, ?> m = (Map<?, ?>)list.get(i);
					resultMap.put("custID", m.get("OLD_PARTY_CODE"));
					resultMap.put("custName",m.get("CUST_NAME"));
					resultMap.put("contactNum", m.get("MOBILE_PHONE"));
					resultList.add(resultMap);
				}
			}

		}

		result.setRespCode("1");
		result.setRespDesc("客户列表查询成功");
		result.setRespMsg(resultList);



	}








}
