package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门实体类
 * 
 * @author lwz
 * @date 2018.6.11
 */


@ApiModel(value = "OrganizationDSL", description = "组织部门")
@Entity
@Table(name = "address_organization")
public class OrganizationDsl{
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	private String rowID;
	/**
	 * 部门ID
	 */
	@Column(name = "ORGAN_ID")
	private String organizationID;
	// 部门名称
	/**
	 * 部门ID
	 */
	@Column(name = "ORGAN_NAME")
	private String organizationName;
	// 上级部门ID
	/**
	 * 部门ID
	 */
	@Column(name = "PID")
	private String pID;
	// 人数
	/**
	 * 部门ID
	 */
	@Column(name = "ORGAN_USER_COUNT")
	private String userCount;
	// 在线人数，当前止前7天，登录过的人数
	/**
	 * 部门ID
	 */
	@Column(name = "ORGAN_ONLINE_COUNT")
	private String onlineCount;
	/**
	 *  最后修改时间
	 */
	@Column(name = "UPDATE_DATE")
	private String updateDate;
	/**
	 * 是否为最下级部门（无子部门）
	 */
	@Column(name = "FLAG")
	private String flag;
	/**
	 * 1 新增 2 修改 3 删除
	 */
	@Column(name = "STATUS")
	private String type;
	/**
	 *  首字母
	 */
	@Column(name = "ZIMUNAME")
	private String zimuname;
	/**
	 *  排序字段
	 */
	@Column(name = "ORG_ORDER")
	private Integer orderValue;


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

	public String getpID() {
		return pID;
	}

	public void setpID(String pID) {
		this.pID = pID;
	}

	public String getUserCount() {
		return userCount;
	}

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}

	public String getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(String onlineCount) {
		this.onlineCount = onlineCount;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getZimuname() {
		return zimuname;
	}

	public void setZimuname(String zimuname) {
		this.zimuname = zimuname;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
}
