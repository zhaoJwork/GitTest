package com.lin.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户地址
 * 
 * @author liudongdong
 * @date 2018年12月4日
 */


@ApiModel(value = "AddressOrganTempIndex", description = "用户地址")
@Entity
@Data
@Table(name = "address_organ_temp_index", schema="SALES_APP")
public class AddressOrganTempIndex {
	
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ORG_ID")
	private String orgID;
	
	@ApiModelProperty(value = "用户地址id")
	@Column(name = "ORG_ID_INDEX")
	private String orgIdIndex;
	
	@ApiModelProperty(value = "用户地址")
	@Column(name = "ORG_NAME_INDEX")
	private String orgNameIndex;
	
	@ApiModelProperty(value = "用户所在组织名称")
	@Column(name = "ORG_NAME")
	private String orgName;

}
