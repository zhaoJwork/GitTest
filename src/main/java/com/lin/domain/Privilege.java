package com.lin.domain;

//权限
public class Privilege {

	private String privilegeID;
	private String privilegeCode;//编码
	private String privilegeName;

	public String getPrivilegeID() {
		return privilegeID;
	}

	public void setPrivilegeID(String privilegeID) {
		this.privilegeID = privilegeID;
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

}
