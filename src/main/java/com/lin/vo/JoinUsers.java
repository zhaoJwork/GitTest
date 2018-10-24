package com.lin.vo;

import lombok.Data;

/**
 * 极光推送创建人列表
 */
@Data
public class JoinUsers {

    /**
     * 图片地址
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 人员id
     */
    private String customerId;
    /**
     * 群组ID
     */
    private String groupId;
    /**
     * 群组名称
     */
    private String groupName;
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
    
    

}
