package com.lin.domain;

/**
 * 分组与人员关联 实体类
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public class GroupUserBean {

	private String rowID;
	// 分组ID
	private String groupID;
	// 人员ID
	private String groupUser;

	public String getRowID() {
		return rowID;
	}

	public void setRowID(String rowID) {
		this.rowID = rowID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(String groupUser) {
		this.groupUser = groupUser;
	}

}
