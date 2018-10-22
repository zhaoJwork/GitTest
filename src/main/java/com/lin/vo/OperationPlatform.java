package com.lin.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通讯录--运行平台实体类
 * @author liudongdong
 * @date 2018年10月15日
 *
 */
@ApiModel(value="operationPlatform", description="运行平台")
@Entity
public class OperationPlatform implements Serializable{
	
	@ApiModelProperty(value = "主键")
	@Id
	private Integer rowId;
	
	@ApiModelProperty(value = "用户id")
	private String staffID;
	
	@ApiModelProperty(value = "用户名称")
	private String staffName;

	@ApiModelProperty(value = "crm用户账号")
	private String crmAccount;
	
	@ApiModelProperty(value = "用户手机号")
	private String telNum;
	
	@ApiModelProperty(value = "部门id")
	private String deptID;
	
	@ApiModelProperty(value = "部门名称")
	private String deptName;
	
	@ApiModelProperty(value = "组织架构")
	private String address;
	
	@ApiModelProperty(value = "最后更新时间")
	private String updateDate;
	
	@ApiModelProperty(value = "账号状态 0 正常  1非正常")
	private String statusCD;
	
	@ApiModelProperty(value = "黑名单 0 正常 1非正常")
	private String blackType;
	
	@ApiModelProperty(value = "是否登入")
	private String isLogin;
	
	@ApiModelProperty(value = "用户头像")
	private String userImg;
	
	@ApiModelProperty(value = "上线时间")
	private String loginTime;
	
	@ApiModelProperty(value = "用户性别 0 男 1 女")
	private String sex;
	
	@ApiModelProperty(value = "用户email")
	private String email;
	
	@ApiModelProperty(value = "黑名单人员id")
	private String userID;
	
	@ApiModelProperty(value = "用户账号状态")
	private String type;
	
	@ApiModelProperty(value = "列表显示数量")
	private String pageSize;
	
	@ApiModelProperty(value = "列表显示页数")
	private String pageNum;

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCrmAccount() {
		return crmAccount;
	}

	public void setCrmAccount(String crmAccount) {
		this.crmAccount = crmAccount;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatusCD() {
		return statusCD;
	}

	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
	}

	public String getBlackType() {
		return blackType;
	}

	public void setBlackType(String blackType) {
		this.blackType = blackType;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}
