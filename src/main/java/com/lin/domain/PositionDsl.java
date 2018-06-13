package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Position 映射类 职务
 * 
 * @author lwz
 * @time 2018.6.13
 */
@ApiModel(value = "PositionDsl", description = "职务表")
@Entity
@Table(name = "ADDRESS_POSITION")
public class PositionDsl {
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	private String rowId;
	@Column(name = "POS_ID")
	private String posId;
	@Column(name = "POS_NAME")
	private String posName;
	@Column(name = "CREATE_DATE")
	private Date createDate;
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
