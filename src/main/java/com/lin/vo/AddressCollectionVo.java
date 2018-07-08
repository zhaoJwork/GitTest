package com.lin.vo;

import com.google.common.base.Strings;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 前台
 * @author lwz
 * @date 2018年6月7号
 */
@ApiModel(value="addressCollectionVo", description="收藏入参 ")
@Data
public class AddressCollectionVo implements Serializable{

	private Integer rowId;

	@ApiModelProperty(value = "收藏人id")
	private Integer collectionLoginId;

	@ApiModelProperty(value = "被收藏人id")
	private Integer collectionUserId;

	@ApiModelProperty(value = "类型 1 收藏 2 其他 0 默认")
	private Integer type;

	@ApiModelProperty(value = "收藏类型  1 收藏 2 取消收藏 0 默认")
	private Integer collectionType;

	@ApiModelProperty(value = "来源  1 企业 2 客户 0 默认")
	private Integer source;

	@ApiModelProperty(value = "收藏修改人id")
	private Integer collectionUpdateBy;

	@ApiModelProperty(value = "全拼")
	private String  quanPin;

	@ApiModelProperty(value = "首字母")
	private String  shouZiMu;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "名称")
	private String name;

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

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getCollectionUpdateBy() {
		return collectionUpdateBy;
	}

	public void setCollectionUpdateBy(Integer collectionUpdateBy) {
		this.collectionUpdateBy = collectionUpdateBy;
	}

	public String getQuanPin() {
		return quanPin;
	}

	public void setQuanPin(String quanPin) {
		this.quanPin = quanPin;
	}

	public String getShouZiMu() {
		return shouZiMu;
	}

	public void setShouZiMu(String shouZiMu) {
		this.shouZiMu = shouZiMu;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
