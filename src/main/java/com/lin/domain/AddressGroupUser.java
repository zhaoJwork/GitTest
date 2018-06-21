package com.lin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分组实体类与数据表之间的对应
 * @author liudongdong
 * @date 2018年6月19日
 *
 */
@ApiModel(value="addressGroupUser", description="通讯录用户分组")
@Entity
@Table(name="ADDRESS_GROUP_USER")
public class AddressGroupUser implements Serializable{

	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_GROUP_USER")
	@SequenceGenerator(name="SEQ_ADDRESS_GROUP_USER", sequenceName="seq_app_addresslist")
	private String rowId;
	
	@ApiModelProperty(value = "组id")
	@Column(name = "GROUP_ID")
	private String groupId;
	
	@ApiModelProperty(value = "用户组名称")
	@Column(name = "GROUP_USER")
	private String groupUser;
	
	@ApiModelProperty(value = "组创建时间")
	@Column(name = "CREATE_DATE")
	private Date createDate;
	

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(String groupUser) {
		this.groupUser = groupUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "AddressGroupUser [rowId=" + rowId + ", groupId=" + groupId + ", groupUser=" + groupUser
				+ ", createDate=" + createDate + "]";
	}

	
	
	
}
