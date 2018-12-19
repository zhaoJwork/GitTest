package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户账户登入信息
 * 数据库--部分字段
 * @author liudongdong
 * @date 2018年10月22日
 *
 */
@ApiModel(value = "userLongJournal", description = "登入信息")
@Entity
@Data
@Table(name = "USERLOGIN_JOURNAL", schema = "appuser")
public class UserLongJournal {
	
	@ApiModelProperty(value = "最近登入日期")
	@Id
	@Column(name = "LOGIN_DATE")
	private String loginDate;
	
	@ApiModelProperty(value = "用户主键")
	@Column(name = "STAFF_ID")
	private String staffID;

	@ApiModelProperty(value = "用户账号")
	@Column(name = "USERNAME")
	private String userName;

	

}
