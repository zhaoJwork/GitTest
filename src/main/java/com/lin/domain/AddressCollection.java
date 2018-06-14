package com.lin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 收藏实体类
 * @author liudongdong
 * @date 2018年6月7号
 */
@ApiModel(value="addressCollection", description="收藏 ")
@Entity
@Table(name="address_collection")
public class AddressCollection implements Serializable{

	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_COLLECTION")
	@SequenceGenerator(name="SEQ_ADDRESS_COLLECTION", sequenceName="seq_app_addresslist")
	private Integer rowId;
	
	@ApiModelProperty(value = "收藏人id")
	@Column(name = "COLLECTION_LOGINID")
	private Integer collectionLoginId;
	
	@ApiModelProperty(value = "被收藏人id")
	@Column(name = "COLLECTION_USERID")
	private Integer collectionUserId;
	
	@ApiModelProperty(value = "类型 1 收藏 2 其他 0 默认")
	@Column(name = "TYPE")
	private Integer type;
	
	@ApiModelProperty(value = "收藏类型  1 收藏 2 取消收藏 0 默认")
	@Column(name = "COLLECTION_TYPE")
	private Integer collectionType;

	@ApiModelProperty(value = "来源  1 企业 2 客户 0 默认")
	@Column(name = "SOURCE")
	private Integer source;
	
	@ApiModelProperty(value = "收藏创建时间")
	@Column(name = "COLLECTION_CREATEDATE")
	private Date collectionCreateDate;
	
	@ApiModelProperty(value = "收藏修改人id")
	@Column(name = "COLLECTION_UPDATEBY")
	private Integer collectionUpdateBy;
	
	@ApiModelProperty(value = "收藏修改时间")
	@Column(name = "COLLECTION_UPDATEDATE")
	private Date collectionUpdateDate;

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public Integer getCollectionLoginId() {
		return collectionLoginId;
	}

	public void setCollectionLoginId(Integer collectionLoginId) {
		this.collectionLoginId = collectionLoginId;
	}

	public Integer getCollectionUserId() {
		return collectionUserId;
	}

	public void setCollectionUserId(Integer collectionUserId) {
		this.collectionUserId = collectionUserId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(Integer collectionType) {
		this.collectionType = collectionType;
	}


	public Integer getCollectionUpdateBy() {
		return collectionUpdateBy;
	}

	public void setCollectionUpdateBy(Integer collectionUpdateBy) {
		this.collectionUpdateBy = collectionUpdateBy;
	}

	
	
	public Date getCollectionCreateDate() {
		return collectionCreateDate;
	}

	public void setCollectionCreateDate(Date collectionCreateDate) {
		this.collectionCreateDate = collectionCreateDate;
	}

	public Date getCollectionUpdateDate() {
		return collectionUpdateDate;
	}

	public void setCollectionUpdateDate(Date collectionUpdateDate) {
		this.collectionUpdateDate = collectionUpdateDate;
	}


	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "AddressCollection [rowId=" + rowId + ", collectionLoginId=" + collectionLoginId + ", collectionUserId="
				+ collectionUserId + ", type=" + type + ", collectionType=" + collectionType + ", collectionCreateDate="
				+ collectionCreateDate + ", collectionUpdateBy=" + collectionUpdateBy + ", collectionUpdateDate="
				+ collectionUpdateDate + "]";
	}
	
	
	
	
}
