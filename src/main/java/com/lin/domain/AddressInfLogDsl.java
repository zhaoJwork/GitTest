package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * 日志
 * @author lwz 2018.6.12
 *
 */
@ApiModel(value = "AddressInfLogBean", description = "通讯录日志")
@Entity
@Table(name = "address_inf_log")
@SequenceGenerator(name="sql_log", sequenceName="seq_app_addressinflog")
public class AddressInfLogDsl {
	@ApiModelProperty(value = "主键")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sql_log")
	@Column(name = "ROW_ID")
	private Integer rowID;
	@Column(name = "ADD_NAME")
	private String addName;
	@Column(name = "REQ_JSON")
	private String reqJson;
	@Column(name = "RESP_JSON")
	private String respJson;
	@Column(name = "CREATE_DATE")
	private Date createDate;
	@Column(name = "END_DATE")
	private Date endDate;
	@Column(name = "EXP_ERROR")
	private String expError;

	public AddressInfLogDsl() {
	}

	public AddressInfLogDsl(Integer rowID, String addName, String reqJson, String respJson,
							Date createDate, Date endDate, String expError) {
		this.rowID = rowID;
		this.addName = addName;
		this.reqJson = reqJson;
		this.respJson = respJson;
		this.createDate = createDate;
		this.endDate = endDate;
		this.expError = expError;
	}

	public Integer getRowID() {
		return rowID;
	}

	public void setRowID(Integer rowID) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExpError() {
		return expError;
	}

	public void setExpError(String expError) {
		this.expError = expError;
	}
}
