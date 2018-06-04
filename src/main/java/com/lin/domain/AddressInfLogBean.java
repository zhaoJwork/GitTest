package com.lin.domain;

/**
 * 日志
 * @author lwz
 *
 */
public class AddressInfLogBean {
	
	private String rowID;
	private String addName;//类型，访问名称
	private String reqJson;
	private String respJson;
	private String createDate;
	private String endDate;
	private String expError;//错误原因

	public AddressInfLogBean() {
	}

	public AddressInfLogBean(String rowID, String addName, String reqJson, String respJson,
			String createDate, String endDate, String expError) {
		this.rowID = rowID;
		this.addName = addName;
		this.reqJson = reqJson;
		this.respJson = respJson;
		this.createDate = createDate;
		this.endDate = endDate;
		this.expError = expError;
	}

	public String getRowID() {
		return rowID;
	}
	public void setRowID(String rowID) {
		this.rowID = rowID;
	}
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	public String getReqJson() {
		return reqJson;
	}
	public void setReqJson(String reqJson) {
		this.reqJson = reqJson;
	}
	public String getRespJson() {
		return respJson;
	}
	public void setRespJson(String respJson) {
		this.respJson = respJson;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExpError() {
		return expError;
	}
	public void setExpError(String expError) {
		this.expError = expError;
	}
	
	
	
	
	
	
}
