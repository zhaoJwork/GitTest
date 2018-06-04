package com.lin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户实体类
 * @author zhangWeiJie
 * @date 2017年8月18日
 */

@ApiModel(value = "User", description = "用户")
@Entity
@Table(name = "address_user")
public class User implements Serializable ,Comparable<User>{
  @JsonIgnore
  public static String picHttpIp;
	/**
	 * 擅长领域
	 */
	@Column(name = "USERTERRITORY")
	private String field;

  @ApiModelProperty(value = "主键")
	@Id
  @Column(name = "ROW_ID")
  private String roleId;
	/**
	 * 用户ID
	 */
  @Column(name = "USER_ID")
	private String userID;
	/**
	 * 省份ID
	 */
	private String provinceID;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 职务
	 */
	@Column(name = "POS_ID")
	private String post;
	/**
	 * 头像地址
	 */
	@Column(name = "PORTRAIT_URL")
	private String userPic;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 工作内容
	 */
	@Column(name = "USERWORK")
	private String context;
	/**
	 * 组织ID
	 */
	@Column(name = "DEP_ID")
	private String organizationID;
	/**
	 * 名称
	 */
	@Column(name = "USER_NAME")
	private String userName;
	/**
	 * 类型 1 新增 2 修改 3 删除
	 */
	@Column(name = "STATUS")
	private String type;
	/**
	 * 最后更新时间 
	 */
	@Column(name = "UPDATE_DATE")
	private String updateDate;
	/**
	 * 是否为领导 1领导 2部门成员
	 */
	@Column(name="POSITION_TYPE")
	private String deptype;
	/**
	 * 姓名全拼
	 */
	private String  quanPin;
	/**
	 * 姓名首字母
	 */
	private String shouZiMu;
	/**
	 * 会议通账号
	 */
	private String hytAccount;
	/**
	 * CRM账号
	 */
	private String crmAccount;
	/**
	 * 是否在线 1 在线 2 不在线
	 */
	private String flagOnline; 
	/**
	 * 人员级别
	 */
	@Column(name = "SORT_NUM")
	private Integer sortNum;
	/**
	 * 排序字段
	 */
	@Column(name = "ORDER_NUM")
	private Integer orderNum;
	/**
	 * 是否安装销售助手,1 安装过,0未安装过
	 */
	private Integer install;
	
	public Integer getInstall() {
		return install;
	}
	public void setInstall(Integer install) {
		this.install = install;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getDeptype() {
		return deptype;
	}
	public void setDeptype(String deptype) {
		this.deptype = deptype;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
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
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getUserPic() {
	  if(Strings.isNullOrEmpty(userPic))
	    return userPic;

		if (userPic.indexOf(picHttpIp)>-1){
			return userPic;
		}else{
			return picHttpIp + userPic;
		}
	}
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	@Override
	public int compareTo(User o) {
		int thisZ = this.orderNum;
		
		int paraZ = o.getOrderNum();
		
		if (thisZ>paraZ) {
			return 1; // 正整数是大于
		} else if (thisZ<paraZ) {
			return -1;// 负整数是小于
		} else {
			return 0; // 0为等于
		}
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

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
}
