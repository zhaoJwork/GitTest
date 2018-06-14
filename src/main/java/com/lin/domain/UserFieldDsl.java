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
 * 用户与擅长领域
 * @author lwz
 * @date 2018.6.13
 */

@ApiModel(value = "UserFieldDsl", description = "用户与擅长领域表")
@Entity
@Table(name = "ADDRESS_USER_TERRITORY")
public class UserFieldDsl implements Serializable {

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
	 * 擅长领域ID
	 */
	@Column(name = "TER_ID")
	private String terID;
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

	public String getTerID() {
		return terID;
	}

	public void setTerID(String terID) {
		this.terID = terID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
