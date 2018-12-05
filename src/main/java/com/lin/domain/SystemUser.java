package com.lin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 
 * @author liudongdong
 * @date 2018年12月4日
 */


@ApiModel(value = "SystemUser", description = "")
@Entity
@Data
@Table(name = "SYSTEM_USER", schema="JTUSER")
public class SystemUser {
	
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "SYSTEM_USER_ID")
	private Integer systemUserID;
	
	@ApiModelProperty(value = "用户id")
	@Column(name = "STAFF_ID")
	private Integer staffID;
	
	@ApiModelProperty(value = "用户状态")
	@Column(name = "STATUS_CD")
	private String statusCD;

}
