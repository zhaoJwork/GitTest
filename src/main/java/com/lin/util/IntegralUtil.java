package com.lin.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class IntegralUtil {
	public static String addScore(String staffId, String busiCode, 
		     String doc, String proType){
	 Map<String, String> reqMap = new HashMap<String, String>();
	 reqMap.put("staffId", staffId);
	 reqMap.put("busiCode", busiCode);
	 reqMap.put("doc", doc);
	 reqMap.put("proType", StringUtils.isBlank(proType)?"0":proType);
	 reqMap.put("platform", "2");
	 String reqString = JsonUtil.mapToString(reqMap); //请求参数
	 String result = "";
	 //String url = ConfigHelper.getString("sales_integral_add_score"); //接口地址 
	 String url = PropUtil.sales_integral_add_score;
	 
	 try {//发送请求
		result = NetUtil.send(url, 
				"POST", 
				reqString, 
				"application/x-www-form-urlencoded;charset=utf-8");
	 } catch (Exception e) {
		e.printStackTrace();
	 }
	 System.out.println("调用结果（end）:"+result);
	 return result;
} 
}
