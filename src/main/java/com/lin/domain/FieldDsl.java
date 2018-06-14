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
 * 擅长领域
 * @author lwz
 * @date 2018.6.13
 */

@ApiModel(value = "FieldDsl", description = "擅长领域表")
@Entity
@Table(name = "address_territory")
public class FieldDsl implements Serializable {

  @ApiModelProperty(value = "主键")
  @Id
  @Column(name = "ROW_ID")
  private String rowID;
	/**
	 * 擅长领域ID
	 */
  @Column(name = "TER_ID")
	private String terID;
	/**
	 * 擅长领域
	 */
	@Column(name = "TER_NAME")
	private String terName;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;

	public String getRowID() {
		return rowID;
	}

	public void setRowID(String rowID) {
		this.rowID = rowID;
	}

	public String getTerID() {
		return terID;
	}

	public void setTerID(String terID) {
		this.terID = terID;
	}

	public String getTerName() {
		return terName;
	}

	public void setTerName(String terName) {
		this.terName = terName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
