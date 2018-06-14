package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户与工作内容关联
 * @author lwz
 * @date 2018.6.13
 */

@ApiModel(value = "UserContextDsl", description = "用户与工作内容表")
@Entity
@Table(name = "address_user_work")
public class UserContextDsl implements Serializable {

  @ApiModelProperty(value = "主键")
  @Id
  @Column(name = "ROW_ID")
  private String rowId;
	/**
	 * 用户ID
	 */
  @Column(name = "USER_ID")
	private String userID;
	/**
	 * 工作内容ID
	 */
	@Column(name = "WORK_ID")
	private String workID;
	/**
	 * 时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getWorkID() {
		return workID;
	}

	public void setWorkID(String workID) {
		this.workID = workID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
