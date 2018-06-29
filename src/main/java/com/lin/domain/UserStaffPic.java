package com.lin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户职员图片
 * @author liudongdong
 * @date 2018年6月22日
 *
 */
@ApiModel(value = "userStaffPic", description = "用户职员图片")
@Entity
@Table(name = "staff_user_pic")
public class UserStaffPic implements Serializable {

	@ApiModelProperty(value = "Id主键")
	@Id
	@Column(name = "ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private String id;
	
	@ApiModelProperty(value = "职员主键")
	@Column(name = "STAFF_ID")
	private String staffID;
	
	@ApiModelProperty(value = "职员图片地址")
	@Column(name = "PIC_URL")
	private String picUrl;
	
	@ApiModelProperty(value = "是否是智慧运营支撑团队")
	@Column(name = "ZH_FLAG")
	private String zhFlag;
	
	@ApiModelProperty(value = "头像状态：1000有效 11100失效")
	@Column(name = "FLAG")
	private String flag;
	
	@ApiModelProperty(value = "上传时间")
	@Column(name = "PIC_DATE")
	private Date picDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getZhFlag() {
		return zhFlag;
	}

	public void setZhFlag(String zhFlag) {
		this.zhFlag = zhFlag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getPicDate() {
		return picDate;
	}

	public void setPicDate(Date picDate) {
		this.picDate = picDate;
	}

	@Override
	public String toString() {
		return "UserStaffPic [id=" + id + ", staffID=" + staffID + ", picUrl=" + picUrl + ", zhFlag=" + zhFlag
				+ ", flag=" + flag + ", picDate=" + picDate + "]";
	}
	
	
}
