package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收藏辅助实体类
 * @author lwz
 * @date 2018年6月28号
 */
@ApiModel(value="AddressColAuxiliary", description="收藏辅助 ")
@Data
@Entity
@Table(name="ADDRESS_COLAUXILIARY")
public class AddressColAuxiliary implements Serializable{

	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "COLLECTION_ROW_ID")
	private Integer rowId;
	
	@ApiModelProperty(value = "全拼")
	@Column(name = "QUANPIN")
	private String quanPin;
	
	@ApiModelProperty(value = "首字符")
	@Column(name = "SHOUZIMU")
	private String shouZiMu;
	
	@ApiModelProperty(value = "名称")
	@Column(name = "NAME")
	private String name;

	@ApiModelProperty(value = "手机号")
	@Column(name = "MOBILE")
	private String mobile;

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
