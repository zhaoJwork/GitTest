package com.lin.domain;

/**
 * 禁言实体类
 * @author liudongdong
 *
 */
public class AddressBanned {

	// 主键
	private Integer rowId;
	
	// 禁言人id
	private Integer bannedSayLoginId;
	
	// 被禁言人id
	private Integer bannedSayUserId;
	
	// 类型    1禁言  2其他  默认0
	private Integer type;
	
	//  禁言类型  1禁言 2 取消禁言 默认0
	private Integer bannedSayType;
	
	// 禁言天数
	private Integer bannedSayDays;
	
	// 禁言截至日期
	private String bannedSayDate;
	
	// 修改日期
	private String updateDate;
	
	// 修改人
	private Integer updateBy;
	
	// 创建日期
	private String createDate;

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

	public String getBannedSayDate() {
		return bannedSayDate;
	}

	public void setBannedSayDate(String bannedSayDate) {
		this.bannedSayDate = bannedSayDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
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
