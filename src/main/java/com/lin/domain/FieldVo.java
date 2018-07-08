package com.lin.domain;
/**
 * 个人资料  擅长领域
 * @author hzh
 * @time 2017-08-19
 */
public class FieldVo {

	//擅长领域ID
	private String fieldID;
	//擅长领域
	private String field;
	//1选中 2 未选中
	private String flag;
	private String rowId;
	private String userID;
	
	
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

	public String getFieldID() {
		return fieldID;
	}

	public void setFieldID(String fieldID) {
		this.fieldID = fieldID;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
