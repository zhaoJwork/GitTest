package com.lin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author liudongdong
 * @date 2018年6月28日
 *
 */
@ApiModel(value = "dKLogourl", description = "")
@Entity
@Table(name = "dk_logourl", schema = "sales")
public class DKLogourl {

	@ApiModelProperty(value = "")
	@Id
	@Column(name = "ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private Integer id;
	
	@ApiModelProperty(value = "ios_logo路径")
	@Column(name = "TWOX_URL")
	private String twoxUrl;
	
	@ApiModelProperty(value = "客户树节点编码")
	@Column(name = "CUST_NODE_CD")
	private String custNodeCD;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTwoxUrl() {
		return twoxUrl;
	}

	public void setTwoxUrl(String twoxUrl) {
		this.twoxUrl = twoxUrl;
	}

	public String getCustNodeCD() {
		return custNodeCD;
	}

	public void setCustNodeCD(String custNodeCD) {
		this.custNodeCD = custNodeCD;
	}

	
	
}
