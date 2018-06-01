package com.lin.domain;

import com.lin.util.PropUtil;
/**
 * 分组实体类
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public class Group {

	// 分组ID
	private String groupID;
	// 分组名称
	private String groupName;
	//群组头像图片
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
	public String getGroupImg() {
		return PropUtil.PIC_HTTPIP+groupImg;
	}
	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg;
	}
}
