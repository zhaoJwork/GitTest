package com.lin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户账户登入信息
 * 数据库--部分字段
 * @author liudongdong
 * @date 2018年10月22日
 *
 */
@ApiModel(value = "userLongJournal", description = "登入信息")
@Entity
@Table(name = "USERLOGIN_JOURNAL", schema = "appuser")
public class UserLongJournal {
	
	@ApiModelProperty(value = "最近登入日期")
	@Id
	@Column(name = "LOGIN_DATE")
	private String loginDate;
	
	@ApiModelProperty(value = "用户主键")
	@Column(name = "STAFF_ID")
	private String staffID;

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	

}
