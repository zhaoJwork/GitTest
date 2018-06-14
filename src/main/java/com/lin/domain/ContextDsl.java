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
 * 工作内容实体类
 * @author lwz
 * @date 2018.6.13
 */

@ApiModel(value = "ContextDsl", description = "工作内容表")
@Entity
@Table(name = "address_user_work")
public class ContextDsl implements Serializable {

  @ApiModelProperty(value = "主键")
  @Id
  @Column(name = "ROW_ID")
  private String rowId;
	/**
	 * 工作内容ID
	 */
  @Column(name = "WORK_ID")
	private String workID;
	/**
	 * 工作内容
	 */
	@Column(name = "WORK_NAME")
	private String workName;
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

	public String getWorkID() {
		return workID;
	}

	public void setWorkID(String workID) {
		this.workID = workID;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
