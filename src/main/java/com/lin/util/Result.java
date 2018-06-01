package com.lin.util;

/**
 * 返回数据
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public class Result {

	/**
	 * 响应编码。定义: 1:正常响应 2:查询失败，服务错误
	 */
	private String respCode;
	/**
	 * 响应描述
	 */
	private String respDesc;
	/**
	 * 响应内容
	 */
	private Object respMsg;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public Object getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(Object respMsg) {
		this.respMsg = respMsg;
	}
}
