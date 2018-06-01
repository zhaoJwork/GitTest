package com.lin.domain;

import java.util.List;
import java.util.Map;

/**
 * 分组详情实体类
 * 
 * @author lwz
 * @date 2017年8月20日
 */
public class GroupDetails {

	// 分组ID
	private String groupID;
	// 分组名称
	private String groupName;
	// 分组详情
	private String groupDesc;
	private List<Map> user;
	// 群组头像图片
	private String groupImg;

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Map> getUser() {
		return user;
	}

	public void setUser(List<Map> user) {
		this.user = user;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupImg() {
		return groupImg;
	}

	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg;
	}

}
