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
@ApiModel(value = "custTreeNode", description = "")
@Entity
@Table(name = "cust_tree_node", schema = "jtorder")
public class CustTreeNode {

	@ApiModelProperty(value = "客户树节点标识")
	@Id
	@Column(name = "CUST_NODE_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private Integer custNodeID;
	
	@ApiModelProperty(value = "客户树节点编码")
	@Column(name = "CUST_NODE_CD")
	private Integer custNodeCD;

	public Integer getCustNodeID() {
		return custNodeID;
	}

	public void setCustNodeID(Integer custNodeID) {
		this.custNodeID = custNodeID;
	}

	public Integer getCustNodeCD() {
		return custNodeCD;
	}

	public void setCustNodeCD(Integer custNodeCD) {
		this.custNodeCD = custNodeCD;
	}

	
}
