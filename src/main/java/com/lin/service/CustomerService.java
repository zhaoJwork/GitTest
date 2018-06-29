package com.lin.service;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chtq.crm.sales.common.Xml2Bean;
import com.cthq.crm.webservice.sales.SalesPADServiceLocator;
import com.cthq.crm.webservice.sales.SalesPADSoapBindingStub;
import com.lin.domain.QCust;
import com.lin.domain.QCustTreeNode;
import com.lin.domain.QCustTreeRel;
import com.lin.domain.QDKLogourl;
import com.lin.domain.QUserStaff;
import com.lin.repository.AddressCollectionRepository;
import com.lin.util.Result;
import com.lin.util.XmlReqAndRes;
import com.lin.util.XmlUtils;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.swagger.models.Xml;

/**
 * 
 * 客户通讯录
 * @author liudongdong
 * @date 2018年6月27日
 *
 */
@Service
public class CustomerService  {
	
	@Value("${application.ADDB_DK}")
	private String addressBookDKUrl;
	
	@Value("${application.CUST_IMG}")
	private String custIMG;
	
	
	
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
	
    public void getContactList(String loginId, String search, String custID, String pageSize, String pageNum,
    		Result result) {
    	// 获取部门
    	QUserStaff qUserStaff = QUserStaff.userStaff;
    	Integer groupId = queryFactory.select(qUserStaff.departmentID).from(qUserStaff)
    	.where(qUserStaff.staffID.eq(Integer.parseInt(loginId))).fetchOne();
    	
    	//准备调用DK参数
		String busiCode = "CustOmer";
		
		String OLD_PARTY_CODE = "";// 客户编码
		String STATUS = "12";// 11模糊查询、12精确查询
		String PAGENUM = (pageSize == null || pageSize == "") ? "1" : pageSize;// 条数
		String NUMBER =(pageNum == null || pageNum == "") ? "20" : pageNum;//页数
		String ORDERBY = "1";// 降序
		
		SalesPADServiceLocator locator = new SalesPADServiceLocator();
		URL url;
		try {
//    		url = new java.net.URL("http://10.128.49.81:9083/CRM-PAD/services/SalesPAD?wsdl");
			url = new URL(addressBookDKUrl);
			SalesPADSoapBindingStub stub = new SalesPADSoapBindingStub(url,locator);
			String reqXml = getReqXml(busiCode, OLD_PARTY_CODE, STATUS, loginId, groupId, search, custID, NUMBER, PAGENUM, ORDERBY);
			System.out.println(reqXml);
			String salesPADService = stub.salesPADService(reqXml);
			System.out.println(salesPADService);
			Document resultDoc = Xml2Bean.createDoc(salesPADService);
		
			// 解析xml
			List<Map<String, String>> list = new ArrayList<>();
			Map<String, String> map;
			List<Element> nodes = resultDoc.selectNodes("//SvcCont/ADD_CUST_CONTACTS");
			Collection<Integer> list2 = new ArrayList<Integer>();
			for(int i = 0; i < nodes.size(); i++) {
				map = new HashMap<>();
				Element element = nodes.get(i);
				String contactId = element.selectSingleNode("CONTACT_ID").getText();
				String contactName = element.selectSingleNode("CONTACT_NAME").getText();
				map.put("contactID", contactId);// 联系人名称
				map.put("contactName", contactName);// 联系人编码
				map.put("contactMobile", element.selectSingleNode("MOBILE_PHONE").getText());// 联系人手机号
				map.put("contactEmail", element.selectSingleNode("E_MAIL").getText());// 联系人Email
				map.put("contactDept", element.selectSingleNode("DEPT_NAME").getText());// 联系人所属部门
				map.put("contactPOST", element.selectSingleNode("POST_NAME").getText());// 联系人职位名称
				
				String oldPartyCode = element.selectSingleNode("OLD_PARTY_CODE").getText();
				map.put("oldPartyCode", oldPartyCode);// 客户编码
				map.put("industry", element.selectSingleNode("INDUSTRY").getText());// 行业编码
				
				// 该联系人是否收藏    true 已被收藏    false未被收藏
				String isCollection;
				if(null == contactId || contactId == "") {
					isCollection = "0";
				}else {
					isCollection = permission.getIsCollection(contactId);
				}
				map.put("collection", isCollection);
				
				 
				// 全拼  首字母  select f_get_hzpy('123张三sss')   from dual
				List<?> resultList = entityManager.createNativeQuery("select f_get_hzpy(?)   from dual")
		    	.setParameter(1, contactName).getResultList();
				map.put("quanPin", resultList.get(0).toString());
				map.put("shouZiMu", resultList.get(0).toString().substring(0,1));
				
				list.add(map);
				if(oldPartyCode != null && oldPartyCode != "") {
					list2.add(Integer.parseInt(oldPartyCode));
				}
			}
			
			
			// img处理   进行行业图片替换
			/*for(int i = 0; i < list.size(); i++) {
				String industry = list.get(i).get("industry").substring(0, 2);
				if (industry.equals("FF")) {//制造业
					list.get(i).put("img", custIMG + "zhizaonengyuan.png");
				}
				if (industry.equals("EE")) {//医疗卫生
					list.get(i).put("img", custIMG + "yiliaoweisheng.png");
				}
				if (industry.equals("DD")) {//金融业
					list.get(i).put("img", custIMG + "jinrongye.png");
				}
				if (industry.equals("CC")) {//交通物流
					list.get(i).put("img", custIMG + "jiaotongwuliu.png");
				}
				if (industry.equals("II")) {//现代农业
					list.get(i).put("img", custIMG + "xiandainongye.png");
				}
				if (industry.equals("HH")) {//商业客户
					list.get(i).put("img", custIMG + "shangyekehu.png");
				}
				if (industry.equals("BB")) {//服务业
					list.get(i).put("img", custIMG + "fuwuhangye.png");
				}
				if (industry.equals("GG")) {//教育行业
					list.get(i).put("img", custIMG + "jiaoyuhangye.png");
				}
				if (industry.equals("AA")) {//政务行业
					list.get(i).put("img", custIMG + "zhengwuhangye.png");
				}
			}*/
			
			// 客户编码list  进行客户图片替换
			QCust qCust = QCust.cust;
			QCustTreeRel qCustTreeRel = QCustTreeRel.custTreeRel;
			QCustTreeNode qCustTreeNode = QCustTreeNode.custTreeNode;
			QDKLogourl qdkLogourl = QDKLogourl.dKLogourl;
			
			/* select t1.cust_id,dl.twox_url from jtorder.cust t1
			 left join jtorder.cust_tree_rel re
			       on t1.cust_id = re.cust_id
			 left join  jtorder.cust_tree_node node 
			 on node.cust_node_id =   re.cust_node_id  
			  left join sales.dk_logourl dl
			    on dl.cust_node_cd = node.cust_node_cd
			 where  t1.cust_id = '4057591'*/
			
			
			List<Tuple> fetch = queryFactory.select(
					qCust.custID, 
					qdkLogourl.twoxUrl
					)
			.from(qCust)
			.leftJoin(qCustTreeRel).on(qCust.custID.eq(qCustTreeRel.custID))
			.leftJoin(qCustTreeNode).on(qCustTreeNode.custNodeID.eq(qCustTreeRel.custNodeID))
			.leftJoin(qdkLogourl).on(qdkLogourl.custNodeCD.eq(qCustTreeNode.custNodeCD.stringValue()))
			.where(qCust.custID.in(list2))
			.fetch();
			
			
			if(fetch.size() > 0) {
				for (Tuple tuple : fetch) {
					System.out.println("firstName " + tuple.get(qCust.custID));
				     System.out.println("lastName " + tuple.get(qdkLogourl.twoxUrl));
				}
			}
			
			
			result.setRespCode("1");
			result.setRespDesc("数量查询成功");
			result.setRespMsg(list);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    

	/*
	 * 根据保存的附件生成对应的请求报文
	 */
	private String getReqXml(String busiCode, String OldPartyCode, String sTATUS, String loginId, Integer groupId, String search,
			String custID, String nUMBER, String pAGENUM, String oRDERBY){
		StringBuffer ret = new StringBuffer();
		ret.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ret.append("<ContractRoot>");
		ret.append("<TcpCont>");
		ret.append("<BusCode>"+busiCode+"</BusCode>");
//		String d = simpleDateFormat.format(new Date());//格式为：yyyyMMddHHmmssSSS(17位)
//		int num = new Random().nextInt(89)+10;//产生10-98之间的随机数(2位)
//		ret.append("<TransactionID>" + d + num + "</TransactionID>");//得到长度为19位的交易编码
//		ret.append("<TimeStamp>" + d + "</TimeStamp>");
		ret.append("</TcpCont>");
		ret.append("<SvcCont>");
		ret.append("<ReqInfo>");
		
		ret.append("<OLD_PARTY_CODE>"+OldPartyCode+"</OLD_PARTY_CODE>");
		ret.append("<STAFF_ID>"+loginId+"</STAFF_ID>");
		ret.append("<DEPARTMENT_ID>"+groupId+"</DEPARTMENT_ID>");
		ret.append("<CONTACT_ID>"+custID+"</CONTACT_ID>");
		ret.append("<CUSTCONTACT_NAME>"+search+"</CUSTCONTACT_NAME>");
		ret.append("<STATUS>"+sTATUS+"</STATUS>");
		ret.append("<NUMBER>" + nUMBER + "</NUMBER>");
		ret.append("<PAGENUM>" + pAGENUM + "</PAGENUM>");
		ret.append("<ORDERBY>" + oRDERBY + "</ORDERBY>");
		
		ret.append("</ReqInfo>");
		ret.append("</SvcCont>");
		ret.append("</ContractRoot>");
		return ret.toString();
	}

	

	public void getCustList(String loginId, String search, String pageSize, String pageNum, Result result) {
		//准备调用DK参数
		String busiCode = "CustOmer";
		
		// 获取部门
    	QUserStaff qUserStaff = QUserStaff.userStaff;
    	Integer deptId = queryFactory.select(qUserStaff.departmentID).from(qUserStaff)
    	.where(qUserStaff.staffID.eq(Integer.parseInt(loginId))).fetchOne();
    	
		String STATUS = "10";//2精确查询   5查询当前客户树子节点详细信息（扩展到五级)
		String NUMBER = (pageSize == null || pageSize == "") ? "1" : pageSize;
		String PAGENUM =(pageNum == null || pageNum == "") ? "20" : pageNum;
		String ORDERBY = "1";// 降序
		
		
		Map<String,Object> map = new HashMap<>();
		map.put("CUST_NAME", search);
		map.put("STAFF_ID", loginId);
		map.put("DEPARTMENT_ID", deptId);
		map.put("STATUS", STATUS);
		map.put("NUMBER", NUMBER);
		map.put("PAGENUM", PAGENUM);
		map.put("ORDERBY", ORDERBY);
		
		Map<String, Object> xmlMap = XmlReqAndRes.reqAndRes(busiCode, addressBookDKUrl, map);
		
		if(!"999".equals(((Map<?, ?>)xmlMap.get("TcpCont")).get("ResultCode"))) {
			
			List<?> list = (List<?>)(((Map<?, ?>)xmlMap.get("SvcCont")).get("ADD_CUST_NUM"));
			List<Object> resultList = new ArrayList<>();
			Map<String, Object> resultMap = null;
			if(list.size() > 0) {
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
			result.setRespCode("1");
			result.setRespDesc("数量查询成功");
			result.setRespMsg(resultList);
		}else {
			result.setRespCode("2");
			result.setRespDesc("数量查询失败");
			result.setRespMsg("");
		}
		
		
		
	}



	




}
