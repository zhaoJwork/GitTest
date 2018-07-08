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
@ApiModel(value = "custTreeRel", description = "")
@Entity
@Table(name = "cust_tree_rel", schema = "jtorder")
public class CustTreeRel {

	@ApiModelProperty(value = "客户标识")
	@Id
	@Column(name = "CUST_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private Integer custID;
	
	@ApiModelProperty(value = "客户树节点标识")
	@Column(name = "CUST_NODE_ID")
	private Integer custNodeID;

	public Integer getCustID() {
		return custID;
	}

	public void setCustID(Integer custID) {
		this.custID = custID;
	}

	public Integer getCustNodeID() {
		return custNodeID;
	}

	public void setCustNodeID(Integer custNodeID) {
		this.custNodeID = custNodeID;
	}
	
	
	
	
	
}
