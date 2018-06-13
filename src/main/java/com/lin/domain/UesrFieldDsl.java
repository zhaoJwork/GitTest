package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户与擅长领域
 * @author lwz
 * @date 2018.6.13
 */

@ApiModel(value = "UesrFieldDsl", description = "用户与擅长领域表")
@Entity
@Table(name = "address_user_new")
public class UesrFieldDsl implements Serializable {

  @ApiModelProperty(value = "主键")
  @Id
  @Column(name = "ROW_ID")
  private String rowId;
	/**
	 * 用户ID
	 */
  @Column(name = "USER_ID")
	private String userid;
	/**
	 * 用户ID
	 */
	@Column(name = "PORTRAIT_URL")
	private String portrait_url;
	/**
	 * 用户ID
	 */
	@Column(name = "INSTALL")
	private Integer install;

	public String getRowid() {
		return rowId;
	}

	public void setRowid(String rowId) {
		this.rowId = rowId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPortrait_url() {
		return portrait_url;
	}

	public void setPortrait_url(String portrait_url) {
		this.portrait_url = portrait_url;
	}

	public Integer getInstall() {
		return install;
	}

	public void setInstall(Integer install) {
		this.install = install;
	}
}
