package com.lin.domain;

/**
 * 收藏实体类
 * @author liudongdong
 * @date 2018年6月7号
 */
public class AddressCollection {

	// 收藏主键
	private Integer rowId;
	
	// 收藏人id
	private Integer collectionLoginId;
	
	// 被收藏人id
	private Integer collectionUserId;
	
	// 类型 1 收藏 2 其他 0 默认
	private Integer type;
	
	// 收藏类型  1 收藏 2 取消收藏 0 默认
	private Integer collectionType;
	
	// 收藏创建时间
	private String collectionCreateDate;
	
	// 收藏修改人id
	private Integer collectionUpdateBy;
	
	// 收藏修改时间
	private String collectionUpdateDate;

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

	public String getCollectionCreateDate() {
		return collectionCreateDate;
	}

	public void setCollectionCreateDate(String collectionCreateDate) {
		this.collectionCreateDate = collectionCreateDate;
	}

	public Integer getCollectionUpdateBy() {
		return collectionUpdateBy;
	}

	public void setCollectionUpdateBy(Integer collectionUpdateBy) {
		this.collectionUpdateBy = collectionUpdateBy;
	}

	public String getCollectionUpdateDate() {
		return collectionUpdateDate;
	}

	public void setCollectionUpdateDate(String collectionUpdateDate) {
		this.collectionUpdateDate = collectionUpdateDate;
	}

	@Override
	public String toString() {
		return "AddressCollection [rowId=" + rowId + ", collectionLoginId=" + collectionLoginId + ", collectionUserId="
				+ collectionUserId + ", type=" + type + ", collectionType=" + collectionType + ", collectionCreateDate="
				+ collectionCreateDate + ", collectionUpdateBy=" + collectionUpdateBy + ", collectionUpdateDate="
				+ collectionUpdateDate + "]";
	}
	
	
	
	
}
