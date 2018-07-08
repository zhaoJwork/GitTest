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
 * 中国电信的内部机构
 * @author liudongdong
 * @date 2018年6月22日
 *
 */
@ApiModel(value = "userOrganization", description = "中国电信的内部机构")
@Entity
@Table(name = "organization")
public class UserOrganization implements Serializable {

	@ApiModelProperty(value = "组织标识主键")
	@Id
	@Column(name = "ORG_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private Integer orgID;
	
	@ApiModelProperty(value = "记录区域标识")
	@Column(name = "COMMON_REGION_ID")
	private Integer commonRegionId;
	
	@ApiModelProperty(value = "内部组织   外部组织")
	@Column(name = "ORG_TYPE")
	private String orgType;
	
	@ApiModelProperty(value = "记录组织简介")
	@Column(name = "ORG_CONTENT")
	private String orgContent;
	
	@ApiModelProperty(value = "记录组织规模")
	@Column(name = "ORG_SCALE")
	private Integer orgScale;
	
	@ApiModelProperty(value = "组织的主要负责人信息")
	@Column(name = "PRINCIPAL")
	private String principal;
	
	@ApiModelProperty(value = "记录内部组织创建时间")
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@ApiModelProperty(value = "状态修改时间")
	@Column(name = "STATUS_DATE")
	private Date statusDate;
	
	@ApiModelProperty(value = "组织名称")
	@Column(name = "ORG_NAME")
	private String orgName;
	
	@ApiModelProperty(value = "组织状态")
	@Column(name = "STATUS_CD")
	private Integer statusCD;
	
	@ApiModelProperty(value = "组织编码")
	@Column(name = "ORG_CODE")
	private String orgCode;
	
	
	@ApiModelProperty(value = "记录创建人员ID")
	@Column(name = "CREATE_USER")
	private Integer createUser;
	
	@ApiModelProperty(value = "记录修改时间")
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@ApiModelProperty(value = "记录修改人员ID")
	@Column(name = "UPDATE_USER")
	private Integer updateUser;
	
	@ApiModelProperty(value = "是否总部")
	@Column(name = "ORG_ZB")
	private String orgZB;
	
	@ApiModelProperty(value = "注册地")
	@Column(name = "ORG_ADDR")
	private String orgAdd;
	
	@ApiModelProperty(value = "组织简称")
	@Column(name = "ORG_JCNAME")
	private String orgJcname;
	
	@ApiModelProperty(value = "参与人ID")
	@Column(name = "PARTY_ID")
	private String partyId;
	
	
	@ApiModelProperty(value = "备注")
	@Column(name = "COMMENTS")
	private String comments;


	public Integer getOrgID() {
		return orgID;
	}


	public void setOrgID(Integer orgID) {
		this.orgID = orgID;
	}


	public Integer getCommonRegionId() {
		return commonRegionId;
	}


	public void setCommonRegionId(Integer commonRegionId) {
		this.commonRegionId = commonRegionId;
	}


	public String getOrgType() {
		return orgType;
	}


	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}


	public String getOrgContent() {
		return orgContent;
	}


	public void setOrgContent(String orgContent) {
		this.orgContent = orgContent;
	}


	public Integer getOrgScale() {
		return orgScale;
	}


	public void setOrgScale(Integer orgScale) {
		this.orgScale = orgScale;
	}


	public String getPrincipal() {
		return principal;
	}


	public void setPrincipal(String principal) {
		this.principal = principal;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getStatusDate() {
		return statusDate;
	}


	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public Integer getStatusCD() {
		return statusCD;
	}


	public void setStatusCD(Integer statusCD) {
		this.statusCD = statusCD;
	}


	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	public Integer getCreateUser() {
		return createUser;
	}


	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public Integer getUpdateUser() {
		return updateUser;
	}


	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}


	public String getOrgZB() {
		return orgZB;
	}


	public void setOrgZB(String orgZB) {
		this.orgZB = orgZB;
	}


	public String getOrgAdd() {
		return orgAdd;
	}


	public void setOrgAdd(String orgAdd) {
		this.orgAdd = orgAdd;
	}


	public String getOrgJcname() {
		return orgJcname;
	}


	public void setOrgJcname(String orgJcname) {
		this.orgJcname = orgJcname;
	}


	public String getPartyId() {
		return partyId;
	}


	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	@Override
	public String toString() {
		return "UserOrganization [orgID=" + orgID + ", commonRegionId=" + commonRegionId + ", orgType=" + orgType
				+ ", orgContent=" + orgContent + ", orgScale=" + orgScale + ", principal=" + principal + ", createDate="
				+ createDate + ", statusDate=" + statusDate + ", orgName=" + orgName + ", statusCD=" + statusCD
				+ ", orgCode=" + orgCode + ", createUser=" + createUser + ", updateDate=" + updateDate + ", updateUser="
				+ updateUser + ", orgZB=" + orgZB + ", orgAdd=" + orgAdd + ", orgJcname=" + orgJcname + ", partyId="
				+ partyId + ", comments=" + comments + "]";
	}
	
	
}
