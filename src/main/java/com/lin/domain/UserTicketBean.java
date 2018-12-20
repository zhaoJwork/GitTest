package com.lin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户账户登入信息
 * 数据库--部分字段
 * @author liudongdong
 * @date 2018年10月22日
 *
 */
@ApiModel(value = "userTicketBean", description = "登入信息")
@Data
@Entity
@Table(name = "USER_TICKET", schema = "SALES_APP")
public class UserTicketBean {

	@ApiModelProperty(value = "最近登入日期")
	@Id
	@Column(name = "CREATE_DATE")
	private Long createDate;
	
	@ApiModelProperty(value = "用户编码")
	@Column(name = "USERNAME")
	private String userName;

	@ApiModelProperty(value = "用户登入时间")
	private String loginDate;
	
}
