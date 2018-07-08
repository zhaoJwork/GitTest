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
@ApiModel(value="addressGroup", description="用户组")
@Entity
@Table(name="address_group")
public class AddressGroup implements Serializable{

	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_GROUP")
	@SequenceGenerator(name="SEQ_ADDRESS_GROUP", sequenceName="seq_app_addresslist")
	private String rowId;
	
	@ApiModelProperty(value = "组id")
	@Column(name = "GROUP_ID")
	private String groupId;
	
	@ApiModelProperty(value = "组名称")
	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@ApiModelProperty(value = "组描述")
	@Column(name = "GROUP_DESC")
	private String groupDesc;
	
	@ApiModelProperty(value = "创建人")
	@Column(name = "CREATE_USER")
	private String createUser;
	
	@ApiModelProperty(value = "组创建时间")
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@ApiModelProperty(value = "组修改时间")
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@ApiModelProperty(value = "组图片")
	@Column(name = "GROUP_IMG")
	private String groupImg;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public String getGroupImg() {
		return groupImg;
	}

	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg;
	}

	@Override
	public String toString() {
		return "AddressGroup [rowId=" + rowId + ", groupId=" + groupId + ", groupName=" + groupName + ", groupDesc="
				+ groupDesc + ", createUser=" + createUser + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", groupImg=" + groupImg + "]";
	}
	
	
	
	
}
