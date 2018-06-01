package com.lin.domain;

/**
 * 个人资料  工作内容
 * @author hzh
 * @time 2017-08-19
 */
public class ContextVo {

	//工作内容ID
	private String contextID;
	//工作内容
	private String context;
	//1选中 2 未选中
	private String flag;
	private String rowId;//当前主键ID
	private String userID;//用户ID
	
	
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getContextID() {
		return contextID;
	}

	public void setContextID(String contextID) {
		this.contextID = contextID;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
