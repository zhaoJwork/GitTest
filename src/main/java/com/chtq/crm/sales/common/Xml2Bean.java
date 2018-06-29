package com.chtq.crm.sales.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.*;
import java.io.*;

/**
 * 服务端解析XML
 * 
 * @author Administrator
 * 
 */
public final class Xml2Bean {

	public static Document createDoc(String reqXml) {

		Document document = null;
		try {
//			reqXml = "<?xml version='1.0' encoding='UTF-8'?><ContractRoot><TcpCont><TransactionID>1000000091201311210000019567</TransactionID><ActionCode>1</ActionCode><RspTime>20131121134642</RspTime><Response><RspType>0</RspType><RspCode>0000</RspCode></Response></TcpCont><SvcCont><SOO type='SAVE_COMPLAINT_WORKSHEET_REQ_TYPE'><PUB_RES><SOO_ID>1</SOO_ID><RspType>0</RspType><RspCode>0000</RspCode><RspDesc>操作成功!!       生成的工单号为:TS20131121-00004</RspDesc></PUB_RES></SOO></SvcCont></ContractRoot>";
			document = DocumentHelper.parseText(reqXml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * xpath 匹配查找
	 */
	public static String selectElementText(Document doc, String xpathStr) {
		Element element = (Element) doc.selectSingleNode(xpathStr);
		return null != element ? element.getText() : null;
	}
	
	public static String getDocXml(String reqXml) {
		return createDoc(reqXml).asXML();
	}

	
	
	/**
	 * 创建报文大体结构
	 * 
	 * @return
	 */
	public static Document createDoc() {
		Document document = DocumentHelper.createDocument();
		Element contractroot = document.addElement("ContractRoot");
		contractroot.addElement("TcpCont");
		Element element = contractroot.addElement("SvcCont");
		element.addElement("RespInfo");
		return document;
	}
	/**
	 *创建报文的大体结构（KPI需要） 
	 * @return
	 */
	public static Document createDoce() {
		Document document = DocumentHelper.createDocument();
		Element contractroot = document.addElement("ContractRoot");
		contractroot.addElement("TcpCont");
		Element element = contractroot.addElement("SvcCont");
		element.addElement("ResInfo");
		return document;
	}
	public static Document createDocForBusOppo() {
		Document document = DocumentHelper.createDocument();
		Element contractroot = document.addElement("ContractRoot");
		contractroot.addElement("TcpCont");
		contractroot.addElement("SvcCont");
		return document;
	}
	
	public static Document createDocForMss(){
		Document document = DocumentHelper.createDocument();
		document.addElement("response");
		return document;
	}
	
	public static void createElement(Element parentElement,
			String elementName, String text) {
		Element element = parentElement.addElement(elementName);
		if (null != text) {
			element.setText(text);
		}
	}
	
	/** 
     * 格式化XML文档 
     * 
     * @param document xml文档 
     * @param charset    字符串的编码 
     * @param istrans    是否对属性和元素值进行转移 
     * @return 格式化后XML字符串 
     */ 
    public static String formatXml(Document document, String charset, boolean istrans) { 
            OutputFormat format = OutputFormat.createPrettyPrint(); 
            format.setEncoding(charset); 
            StringWriter sw = new StringWriter(); 
            XMLWriter xw = new XMLWriter(sw, format); 
            xw.setEscapeText(istrans); 
            try { 
                    xw.write(document); 
                    xw.flush(); 
                    xw.close(); 
            } catch (IOException e) { 
                    System.out.println("格式化XML文档发生异常，请检查！"); 
                    e.printStackTrace(); 
            } 
            return sw.toString(); 
    }
    /**
	 * 创建价格审批大体结构
	 * 
	 * @return
	 */
	public static Document createPriceApprovalDoc() {
		Document document = DocumentHelper.createDocument();
		Element contractroot = document.addElement("ContractRoot");
		contractroot.addElement("TcpCont");
		contractroot.addElement("SvcCont");
		return document;
	}
    public static void main(String[] args) {
		String res="<response><RESULT>S</RESULT><MESSAGE>同步成功</MESSAGE><CONTRACTID>2828572</CONTRACTID></response>";
		Xml2Bean xm=new Xml2Bean();
		Document doc=xm.createDoc(res);
		String SOURCE_ID = Xml2Bean.selectElementText(doc, "//response/RESULT");
		String CONTRACTID = Xml2Bean.selectElementText(doc, "//response/MESSAGE");
		String CONTRACTCODE = Xml2Bean.selectElementText(doc, "//response/CONTRACTID");
		System.out.println(CONTRACTCODE);
	}
    
}