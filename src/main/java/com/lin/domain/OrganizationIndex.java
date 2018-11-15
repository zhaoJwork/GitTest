package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 部门索引实体类
 * 
 * @author 部门索引
 * @date 2018.10.12
 */
@ApiModel(value="OrganizationIndex", description="部门索引")
@Entity
@Data
@Table(name="ADDRESS_ORGAN_INDEX")
public class OrganizationIndex implements Serializable {

	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ORG_ID")
	private String orgID;

	@ApiModelProperty(value = "索引")
	@Column(name = "ORG_ID_INDEX")
	private String orgIDIndex;

	@ApiModelProperty(value = "名称")
	@Column(name = "ORG_NAME")
	private String orgName;

	@ApiModelProperty(value = "索引名称")
	@Column(name = "ORG_NAME_INDEX")
	private String orgNameIndex;

}
