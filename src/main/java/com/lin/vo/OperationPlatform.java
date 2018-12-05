package com.lin.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通讯录--运行平台实体类
 * @author liudongdong
 * @date 2018年10月15日
 *
 */
@ApiModel(value="operationPlatform", description="运行平台")
@Data
@Entity
public class OperationPlatform implements Serializable{
	
	@ApiModelProperty(value = "主键")
	@Id
	private Integer rowId;
	
	@ApiModelProperty(value = "用户id")
	private String staffID;
	
	@ApiModelProperty(value = "用户名称")
	private String staffName;

	@ApiModelProperty(value = "crm用户账号")
	private String crmAccount;
	
	@ApiModelProperty(value = "用户手机号")
	private String telNum;
	
	@ApiModelProperty(value = "部门id")
	private String deptID;
	
	@ApiModelProperty(value = "部门名称")
	private String deptName;
	
	@ApiModelProperty(value = "组织架构")
	private String address;
	
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	@ApiModelProperty(value = "账号状态 0 正常  1非正常")
	private String statusCD;
	
	@ApiModelProperty(value = "黑名单 0 正常 1非正常")
	private String blackType;
	
	@ApiModelProperty(value = "是否登入")
	private String isLogin;
	
	@ApiModelProperty(value = "用户头像")
	private String userImg;
	
	@ApiModelProperty(value = "上线时间")
	private String loginTime;
	
	@ApiModelProperty(value = "用户性别 0 男 1 女")
	private String sex;
	
	@ApiModelProperty(value = "用户email")
	private String email;
	
	@ApiModelProperty(value = "黑名单人员id")
	private String userID;
	
	@ApiModelProperty(value = "用户账号状态")
	private String type;
	
	@ApiModelProperty(value = "列表显示数量")
	private String pageSize;
	
	@ApiModelProperty(value = "列表显示页数")
	private String pageNum;
	
}
