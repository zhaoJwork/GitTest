package com.lin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 禁言实体类
 * @author liudongdong
 *
 */
@ApiModel(value="addressBanned", description="禁言 ")
@Entity
@Table(name="address_bannedsay")
public class AddressBanned implements Serializable{

	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_APPUser_user")
	@SequenceGenerator(name="SEQ_APPUser_user", sequenceName="seq_app_addresslist")
	private Integer rowId;
	
	// 禁言人id
	@Column(name = "BANNEDSAY_LOGINID")
	private Integer bannedSayLoginId;
	
	// 被禁言人id
	@Column(name = "BANNEDSAY_USERID")
	private Integer bannedSayUserId;
	
	// 类型    1禁言  2其他  默认0
	@Column(name = "TYPE")
	private Integer type;
	
	//  禁言类型  1禁言 2 取消禁言 默认0
	@Column(name = "BANNEDSAY_TYPE")
	private Integer bannedSayType;
	
	// 禁言天数
	@Column(name = "BANNEDSAY_DAYS")
	private Integer bannedSayDays;
	
	// 禁言截至日期
	@Column(name = "BANNEDSAY_DATE")
	private Date bannedSayDate;
	
	// 修改日期
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	// 修改人
	@Column(name = "UPDATE_BY")
	private Integer updateBy;
	
	// 创建日期
	@Column(name = "CREATE_DATE")
	private Date createDate;

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public Integer getBannedSayLoginId() {
		return bannedSayLoginId;
	}

	public void setBannedSayLoginId(Integer bannedSayLoginId) {
		this.bannedSayLoginId = bannedSayLoginId;
	}

	public Integer getBannedSayUserId() {
		return bannedSayUserId;
	}

	public void setBannedSayUserId(Integer bannedSayUserId) {
		this.bannedSayUserId = bannedSayUserId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getBannedSayType() {
		return bannedSayType;
	}

	public void setBannedSayType(Integer bannedSayType) {
		this.bannedSayType = bannedSayType;
	}

	public Integer getBannedSayDays() {
		return bannedSayDays;
	}

	public void setBannedSayDays(Integer bannedSayDays) {
		this.bannedSayDays = bannedSayDays;
	}


	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}


	public Date getBannedSayDate() {
		return bannedSayDate;
	}

	public void setBannedSayDate(Date bannedSayDate) {
		this.bannedSayDate = bannedSayDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "AddressBanned [rowId=" + rowId + ", bannedSayLoginId=" + bannedSayLoginId + ", bannedSayUserId="
				+ bannedSayUserId + ", type=" + type + ", bannedSayType=" + bannedSayType + ", bannedSayDays="
				+ bannedSayDays + ", bannedSayDate=" + bannedSayDate + ", updateDate=" + updateDate + ", updateBy="
				+ updateBy + ", createDate=" + createDate + "]";
	}

	
	
	
}
