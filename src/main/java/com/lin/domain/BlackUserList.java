package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据库用户黑名单
 * 
 * @author lwz
 * @date 2018.10.10
 */


@ApiModel(value = "BlackUserList", description = "用户黑名单")
@Entity
@Data
@Table(name = "ADDRESS_BLACKLIST")
public class BlackUserList {
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	private String rowID;
	/**
	 * 部门ID
	 */
	@Column(name = "ORG_ID")
	private String orgID;
	/**
	 * 部门名称
	 */
	@Column(name = "ORG_NAME")
	private String orgName;
	/**
	 * 创建人
	 */
	@Column(name = "CREATE_BY")
	private String createBy;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;

}
