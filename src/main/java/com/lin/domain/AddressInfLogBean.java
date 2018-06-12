package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 日志
 * @author lwz 2018.6.12
 *
 */
@ApiModel(value = "AddressInfLogBean", description = "通讯录日志")
@Entity
@Table(name = "address_inf_log")
public class AddressInfLogBean {
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	private String rowID;
	@Column(name = "ADD_NAME")
	private String addName;
	@Column(name = "REQ_JSON")
	private String reqJson;
	@Column(name = "RESP_JSON")
	private String respJson;
	@Column(name = "CREATE_DATE")
	private String createDate;
	@Column(name = "END_DATE")
	private String endDate;
	@Column(name = "EXP_ERROR")
	private String expError;

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
