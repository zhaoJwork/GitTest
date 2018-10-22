package com.lin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户联系信息
 * 数据库--部分字段
 * @author liudongdong
 * @date 2018年10月19日
 *
 */
@ApiModel(value = "partyContactInfo", description = "联系信息")
@Entity
@Table(name = "jparty_contact_info", schema = "torder")
public class PartyContactInfo {
	
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "CONTACT_ID")
	private Integer contactID;
	
	@ApiModelProperty(value = "记录参与人唯一标识，作为主键")
	@Column(name = "PARTY_ID")
	private Integer partyID;
	
	@ApiModelProperty(value = "用户手机号")
	@Column(name = "MOBILE_PHONE")
	private Integer mobilePhone;
	
	@ApiModelProperty(value = "用户邮箱")
	@Column(name = "E_MAIL")
	private Integer eMail;

	public Integer getContactID() {
		return contactID;
	}

	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

	public Integer getPartyID() {
		return partyID;
	}

	public void setPartyID(Integer partyID) {
		this.partyID = partyID;
	}

	public Integer getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(Integer mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer geteMail() {
		return eMail;
	}

	public void seteMail(Integer eMail) {
		this.eMail = eMail;
	}
	
	

}
