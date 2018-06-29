package com.lin.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * 收藏列表
 * @author  lwz 2018.6.28
 */
@ApiModel(value="AutoCollectionVo", description="收藏列表 ")
@Data
public class AutoCollectionVo {

	/**
	 * address_collection
	 *
	 */
	private Integer rowId;
	/**
	 * address_collection
	 * 数据来源 1企业 2 客户
	 */
	private Integer source;
	/**
	 * address_user
	 * 用户ID
	 */
	private String userID;
	/**
	 * address_user
	 * 用户名称
	 */
	private String userName;
	/**
	 * address_user
	 * 所在省
	 */
	private String provinceID;
	/**
	 * address_user
	 * 电话
	 */
	private String phone;
	/**
	 * address_user
	 * 手机
	 */
	private String email;
	/**
	 * address_user
	 * 地址
	 */
	private String address;
	/**
	 * address_user
	 * 部门
	 */
	private String organizationID;
	/**
	 * address_user
	 * 图片
	 */
	private String userPic;
	/**
	 * address_user
	 * 部门名称
	 */
	private String posName;
	/**
	 * address_user
	 * 是否为领导
	 */
	private String deptype;
	/**
	 * address_user
	 * 全拼
	 */
	private String quanPin;
	/**
	 * address_user
	 * 首字母
	 */
	private String shouZiMu;
	/**
	 * address_user
	 * 会议通账号
	 */
	private String hytAccount;
	/**
	 * address_user
	 * crm账号
	 */
	private String crmAccount;
	/**
	 * address_user
	 * 是否在线
	 */
	private String flagOnline;
	/**
	 * address_user_new
	 * 是否安装
	 */
	private Integer install;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 联系人ID
	 */
	private Integer colAuxContactID;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 联系人名称
	 */
	private String colAuxContactName;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 联系人手机号
	 */
	private String colAuxContactMobile;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 联系人邮箱
	 */
	private String colAuxContactEmail;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 所属部门
	 */
	private String colAuxContactDept;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 职位名称
	 */
	private String colAuxContactPOST;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 头像
	 */
	private String colAuxImg;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 全拼
	 */
	private String colAuxQanPin;
	/**
	 * ADDRESS_COLAUXILIARY
	 * 首字母
	 */
	private String colAuxShouZiMu;
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProvinceID() {
		return provinceID;
	}
	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getUserPic() {
		return userPic;
	}
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public String getDeptype() {
		return deptype;
	}
	public void setDeptype(String deptype) {
		this.deptype = deptype;
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
	public String getHytAccount() {
		return hytAccount;
	}
	public void setHytAccount(String hytAccount) {
		this.hytAccount = hytAccount;
	}
	public String getCrmAccount() {
		return crmAccount;
	}
	public void setCrmAccount(String crmAccount) {
		this.crmAccount = crmAccount;
	}
	public String getFlagOnline() {
		return flagOnline;
	}
	public void setFlagOnline(String flagOnline) {
		this.flagOnline = flagOnline;
	}
	public Integer getInstall() {
		return install;
	}
	public void setInstall(Integer install) {
		this.install = install;
	}
	public Integer getColAuxContactID() {
		return colAuxContactID;
	}
	public void setColAuxContactID(Integer colAuxContactID) {
		this.colAuxContactID = colAuxContactID;
	}
	public String getColAuxContactName() {
		return colAuxContactName;
	}
	public void setColAuxContactName(String colAuxContactName) {
		this.colAuxContactName = colAuxContactName;
	}
	public String getColAuxContactMobile() {
		return colAuxContactMobile;
	}
	public void setColAuxContactMobile(String colAuxContactMobile) {
		this.colAuxContactMobile = colAuxContactMobile;
	}
	public String getColAuxContactEmail() {
		return colAuxContactEmail;
	}
	public void setColAuxContactEmail(String colAuxContactEmail) {
		this.colAuxContactEmail = colAuxContactEmail;
	}
	public String getColAuxContactDept() {
		return colAuxContactDept;
	}
	public void setColAuxContactDept(String colAuxContactDept) {
		this.colAuxContactDept = colAuxContactDept;
	}
	public String getColAuxContactPOST() {
		return colAuxContactPOST;
	}
	public void setColAuxContactPOST(String colAuxContactPOST) {
		this.colAuxContactPOST = colAuxContactPOST;
	}
	public String getColAuxImg() {
		return colAuxImg;
	}
	public void setColAuxImg(String colAuxImg) {
		this.colAuxImg = colAuxImg;
	}
	public String getColAuxQanPin() {
		return colAuxQanPin;
	}
	public void setColAuxQanPin(String colAuxQanPin) {
		this.colAuxQanPin = colAuxQanPin;
	}
	public String getColAuxShouZiMu() {
		return colAuxShouZiMu;
	}
	public void setColAuxShouZiMu(String colAuxShouZiMu) {
		this.colAuxShouZiMu = colAuxShouZiMu;
	}



	

	
	
}
