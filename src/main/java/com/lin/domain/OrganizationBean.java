package com.lin.domain;

/**
 * 部门实体类
 * 
 * @author zhangWeiJie
 * @date 2017年8月19日
 */
public class OrganizationBean implements Comparable<OrganizationBean> {

	// 部门ID
	private String organizationID;
	// 部门名称
	private String organizationName;
	// 上级部门ID
	private String pID;
	// 人数
	private String userCount;
	// 在线人数，当前止前30天，登录过的人数
	private String onlineCount;
	// 最后修改时间
	private String updateDate;
	// 是否为最下级部门（无子部门）
	private String flag;
	// 1 新增 2 修改 3 删除
	private String type;
	// 首字母
	private String zimuname;
	
	private Integer orderValue;

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

	public String getpID() {
		return pID;
	}

	public void setpID(String pID) {
		this.pID = pID;
	}

	public String getUserCount() {
		return userCount;
	}

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}

	public String getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(String onlineCount) {
		this.onlineCount = onlineCount;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getZimuname() {
		return zimuname;
	}

	public void setZimuname(String zimuname) {
		this.zimuname = zimuname;
	}

	@Override
	public int compareTo(OrganizationBean o) {
		int thisZ = this.orderValue;
		
		int paraZ = o.getOrderValue();
		
		if (thisZ>paraZ) {
			return 1; // 正整数是大于
		} else if (thisZ<paraZ) {
			return -1;// 负整数是小于
		} else {
			return 0; // 0为等于
		}
	}
	
	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}

}
