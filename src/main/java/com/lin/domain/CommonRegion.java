package com.lin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 是指对于各种专业电信管理区域的共性管理区域信息的抽象表达
 * @author liudongdong
 * @date 2018年6月22日
 *
 */
@ApiModel(value = "commonRegion", description = "是指对于各种专业电信管理区域的共性管理区域信息的抽象表达")
@Entity
@Table(name = "COMMON_REGION")
public class CommonRegion implements Serializable{

	@ApiModelProperty(value = "记录区域标识")
	@Id
	@Column(name = "COMMON_REGION_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private Integer commonRegionID;
	
	@ApiModelProperty(value = "记录区域标识")
	@Column(name = "UP_REGION_ID")
	private Integer upRegionID;
	
	@ApiModelProperty(value = "记录区域名称")
	@Column(name = "REGION_NAME")
	private String regionName;
	
	@ApiModelProperty(value = "记录区域编码")
	@Column(name = "REGION_CODE")
	private String regionCode;
	
	@ApiModelProperty(value = "记录区域类型")
	@Column(name = "REGION_TYPE")
	private String regionType;
	
	@ApiModelProperty(value = "记录区域描述")
	@Column(name = "REGION_DESC")
	private String regionDesc;
	
	@ApiModelProperty(value = "记录创建时间")
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@ApiModelProperty(value = "状态 ： 1000 正常 1099 删除")
	@Column(name = "STATUS_CD")
	private String statusCD;
	
	@ApiModelProperty(value = "索引，查询用到")
	@Column(name = "INDEX_CODE")
	private String indexCode;
	
	@ApiModelProperty(value = "状态修改时间")
	@Column(name = "STATUS_DATE")
	private Date statusDate;
	
	@ApiModelProperty(value = "记录创建人员")
	@Column(name = "CREATE_USER")
	private Integer createUser;
	
	@ApiModelProperty(value = "记录修改时间记录修改时间记录修改时间")
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@ApiModelProperty(value = "记录修改人员ID")
	@Column(name = "UPDATE_USER")
	private Integer updateUser;

	public Integer getCommonRegionID() {
		return commonRegionID;
	}

	public void setCommonRegionID(Integer commonRegionID) {
		this.commonRegionID = commonRegionID;
	}

	public Integer getUpRegionID() {
		return upRegionID;
	}

	public void setUpRegionID(Integer upRegionID) {
		this.upRegionID = upRegionID;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public String getRegionDesc() {
		return regionDesc;
	}

	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatusCD() {
		return statusCD;
	}

	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
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

	@Override
	public String toString() {
		return "CommonRegion [commonRegionID=" + commonRegionID + ", upRegionID=" + upRegionID + ", regionName="
				+ regionName + ", regionCode=" + regionCode + ", regionType=" + regionType + ", regionDesc="
				+ regionDesc + ", createDate=" + createDate + ", statusCD=" + statusCD + ", indexCode=" + indexCode
				+ ", statusDate=" + statusDate + ", createUser=" + createUser + ", updateDate=" + updateDate
				+ ", updateUser=" + updateUser + "]";
	}
	
	
	
}
