package com.lin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

/**
 * 分组实体类
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public class Group {
	@JsonIgnore
	public static String picHttpIp;
	// 分组ID
	private String groupID;
	// 分组名称
	private String groupName;
	//群组头像图片
	private String groupImg;

	private String groupChatID;

	public String getGroupChatID () { return groupChatID; }

	public void setGroupChatID(String groupChatID) {
		this.groupChatID = groupChatID;
	}

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
		if(Strings.isNullOrEmpty(groupImg)){
			return groupImg;
		}else{
			if(Strings.isNullOrEmpty(groupChatID)){
				return picHttpIp + groupImg;
			}else{
				return groupImg;
			}
		}
	}
	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg;
	}
}
