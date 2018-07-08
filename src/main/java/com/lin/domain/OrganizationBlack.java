package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门黑名单实体类
 * 
 * @author lwz
 * @date 2018.6.11
 */
@ApiModel(value = "OrganizationBlack", description = "组织部门黑名单")
@Entity
@Table(name = "address_blackorglist")
public class OrganizationBlack {
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	private String rowID;
	/**
	 * 部门ID
	 */
	@Column(name = "ORG_ID")
	private String organizationID;
	/**
	 * 部门
	 */
	@Column(name = "ORG_NAME")
	private String organizationName;
	/**
	 * 创建人
	 */
	@Column(name = "CREATE_BY")
	private String createBy;
	/**
	 * 时间
	 */
	@Column(name = "CREATE_DATE")
	private String createDate;

	public String getRowID() {
		return rowID;
	}

	public void setRowID(String rowID) {
		this.rowID = rowID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
