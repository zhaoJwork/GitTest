package com.lin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IntegralUtil {

  @Value("${application.sales_integral_add_score}")
  private String sales_integral_add_score;

  public String addScore(String staffId, String busiCode,
      String doc, String proType) {
    Map<String, String> reqMap = new HashMap<String, String>();
    reqMap.put("staffId", staffId);
    reqMap.put("busiCode", busiCode);
    reqMap.put("doc", doc);
    reqMap.put("proType", Strings.isNullOrEmpty(proType) ? "0" : proType);
    reqMap.put("platform", "2");
    String reqString = null; //请求参数
    try {
      reqString = JsonUtil.toJson(reqMap);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    String result = "";
    String url = sales_integral_add_score;

    try {//发送请求
      result = NetUtil.send(url,
          "POST",
          reqString,
          "application/x-www-form-urlencoded;charset=utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("调用结果（end）:" + result);
    return result;
  }
}
