package com.lin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 
 * @author liudongdong
 * @date 2018年12月4日
 */


@ApiModel(value = "PartyCertification", description = "")
@Entity
@Data
@Table(name = "PARTY_CERTIFICATION", schema="JTORDER")
public class PartyCertification {
	
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "PARTY_CERTI_ID")
	private Integer partyCertiID;
	
	@ApiModelProperty(value = "用户地址")
	@Column(name = "PARTY_ID")
	private Integer partyID;
	
	@ApiModelProperty(value = "")
	@Column(name = "CERT_VALID")
	private String certValID;

}
