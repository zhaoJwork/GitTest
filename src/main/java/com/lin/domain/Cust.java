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
@ApiModel(value = "cust", description = "")
@Entity
@Table(name = "cust", schema = "jtorder")
public class Cust {

	@ApiModelProperty(value = "")
	@Id
	@Column(name = "CUST_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private Integer custID;

	public Integer getCustID() {
		return custID;
	}

	public void setCustID(Integer custID) {
		this.custID = custID;
	}
	
	
	
}
