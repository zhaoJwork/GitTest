package com.lin.domain;
/**
 * 省份和擅长领域
 * @author hzh
 *@time 2017-08-24
 */
public class OrgTer {

	//省份
	private String organizationName;
	//省份编码
	private String organizationId;
	//领域
	private String terName;
	//领域ID
	private String terId;         
	
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getTerId() {
		return terId;
	}

	public void setTerId(String terId) {
		this.terId = terId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTerName() {
		return terName;
	}

	public void setTerName(String terName) {
		this.terName = terName;
	}
	
	
	
	
}
