package com.lin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import lombok.Data;

import java.util.List;

/**
 * 五、个人资料详情查询 实体类
 * 
 * @author lwz
 * @time 2018-06-13
 *
 */
@Data
public class UserDetailsDsl {

	/**
	 * 人员ID
	 */
	private String userID;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 *  头像地址
	 */
	private String userPic;
	/**
	 * 部门ID
	 */
	private String organizationID;
	/**
	 * 部门名称
	 */
	private String organizationName;
	/**
	 * 职务ID
	 */
	private String postID;
	/**
	 * 职务名称
	 */
	private String postName;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 地址
	 */
	private String address;
	/**
     * 工作内容
	 */
	private List<ContextVo> context;
	/**
     * 擅长领域
	 */
	private List<FieldVo> field;
	/**
     * 工作内容
	 */
	private String contexts;
	/**
     * 擅长领域
	 */
	private String fields;
	/**
     * 1 登录过销售助手 0 没有登录过销售助手
	 */
	private int install;

	/**
	 * 是否可以查看能力详情    ability     1 是  0 否
	 */
	private String ability ;
	/**
	 * 是否可以禁言 notalk 1 是 0 否
	 */
	private String notalk ;
	/**
	 * 是否被收藏    collection  1 是 0 否
	 */
	private String collection ;
	/**
	 * 禁言状态       talkstatus 1 是 0 否
	 */
	private String talkstatus ;

	@JsonIgnore
	public static String picHttpIp;
	
	public int getInstall() {
		return install;
	}

	public void setInstall(int install) {
		this.install = install;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPic() {
		if(Strings.isNullOrEmpty(userPic)) {
			return userPic;
		}
		if (userPic.indexOf(picHttpIp) > -1) {
			return userPic;
		} else {
			return picHttpIp + userPic;
		}
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<ContextVo> getContext() {
		return context;
	}

	public void setContext(List<ContextVo> context) {
		this.context = context;
	}

	public List<FieldVo> getField() {
		return field;
	}

	public void setField(List<FieldVo> field) {
		this.field = field;
	}

	public String getContexts() {
		return contexts;
	}

	public void setContexts(String contexts) {
		this.contexts = contexts;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public static String getPicHttpIp() {
		return picHttpIp;
	}

	public static void setPicHttpIp(String picHttpIp) {
		UserDetailsDsl.picHttpIp = picHttpIp;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public String getNotalk() {
		return notalk;
	}

	public void setNotalk(String notalk) {
		this.notalk = notalk;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getTalkstatus() {
		return talkstatus;
	}

	public void setTalkstatus(String talkstatus) {
		this.talkstatus = talkstatus;
	}


}
