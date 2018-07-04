package com.lin.util;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.Element;

import com.chtq.crm.sales.common.Xml2Bean;
import com.cthq.crm.webservice.sales.SalesPADServiceLocator;
import com.cthq.crm.webservice.sales.SalesPADSoapBindingStub;

public class XmlReqAndRes {
	
	private static String DEFAULT_FORMAT = "yyyyMMddHHmmssSSS";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);

	/**
	 * 定义map参数   返回map数据
	 * @param busiCode 报文请求头参数
	 * @param url  远程连接地址
	 * @param map  请求参数  key为节点名称  value为节点值
	 * @return document 转map类信息返回
	 */
	public static Map<String, Object> reqAndRes(String busiCode, String url, Map<String,Object> map) {
		long start = System.currentTimeMillis();
		Map<String, Object> dom2Map = null;
		SalesPADServiceLocator locator = new SalesPADServiceLocator();
		try {
			SalesPADSoapBindingStub stub = new SalesPADSoapBindingStub(new URL(url),locator);
			
			String reqXml = reqXml(busiCode, map);
			System.out.print("dk---------:"+reqXml);
			String salesPADService = stub.salesPADService(reqXml);
			
			long end = System.currentTimeMillis();
			
			System.out.println("调用webservice连接,共消耗"+(end-start)+"ms");
			
			if(salesPADService.equals("程序内部执行异常")) {
				return new HashMap<>();
			}
			
			Document resultDoc = Xml2Bean.createDoc(salesPADService);
			
			dom2Map = XmlUtils.Dom2Map(resultDoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dom2Map;
	}
	
	/**
	 * xml组装
	 * @param busiCode
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String reqXml(String busiCode, Map<String, Object> map) {
		StringBuffer ret = new StringBuffer();
		ret.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ret.append("<ContractRoot>");
		ret.append("<TcpCont>");
		ret.append("<BusCode>"+busiCode+"</BusCode>");
		String d = simpleDateFormat.format(new Date());//格式为：yyyyMMddHHmmssSSS(17位)
		int num = new Random().nextInt(89)+10;//产生10-98之间的随机数(2位)
		ret.append("<TransactionID>" + d + num + "</TransactionID>");//得到长度为19位的交易编码
		ret.append("<TimeStamp>" + d + "</TimeStamp>");
		ret.append("</TcpCont>");
		ret.append("<SvcCont>");
		ret.append("<ReqInfo>");
		
		for (String key : map.keySet()) {
			ret.append("<"+key+">"+map.get(key)+"</"+key+">");
		}
		
		ret.append("</ReqInfo>");
		ret.append("</SvcCont>");
		ret.append("</ContractRoot>");
		return ret.toString();
	}
	
}
