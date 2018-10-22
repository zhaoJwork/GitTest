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
	
	@ApiModelProperty(value = "用户id")
	@Column(name = "USER_ID")
	private String userID;
	
	@ApiModelProperty(value = "用户name")
	@Column(name = "USER_NAME")
	private String userName;
	
	@ApiModelProperty(value = "创建人")
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@ApiModelProperty(value = "创建时间")
	@Column(name = "CREATE_DATE")
	private Date createDate;


	public String getRowID() {
		return rowID;
	}

	public void setRowID(String rowID) {
		this.rowID = rowID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
