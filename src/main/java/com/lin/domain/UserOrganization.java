package com.lin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中国电信的内部机构
 * @author liudongdong
 * @date 2018年6月22日
 *
 */
@ApiModel(value = "userOrganization", description = "中国电信的内部机构")
@Entity
@Data
@Table(name = "organization", schema="JTUSER")
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

	
	
}
