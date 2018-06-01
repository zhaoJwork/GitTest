package com.lin.domain;

import java.io.Serializable;

import com.lin.util.PropUtil;

/**
 * 用户实体类
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
/**
 * {
 * "field":"互联网、物联网",y
 * "userID":"123",y
 * "provinceID":"12345",y
 * "phone":"15900000000",y
 * "post":"大客户经理",y
 * "userPic":"/pic.pic",y
 * "address":"北京",y
 * "email":"15900000000@qq.com",y
 * "context":"联系大客户、大客户维护",y
 * "organizationID":"12345",
 * "userName":"小明",y
 * "type":"1,2"}y
 *
 */
public class User implements Serializable ,Comparable<User>{
	/**
	 * 擅长领域
	 */
	private String field;
	/**
	 * 用户ID
	 */
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
	private String post;
	/**
	 * 头像地址
	 */
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
	private String context;
	/**
	 * 组织ID
	 */
	private String organizationID;
	/**
	 * 名称
	 */
	private String userName;
	/**
	 * 类型 1 新增 2 修改 3 删除
	 */
	private String type;
	/**
	 * 最后更新时间 
	 */
	private String updateDate;
	/**
	 * 是否为领导 1领导 2部门成员
	 */
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
	private int sortNum;
	/**
	 * 排序字段
	 */
	private int orderNum;
	/**
	 * 是否安装销售助手,1 安装过,0未安装过
	 */
	private int install;
	
	public int getInstall() {
		return install;
	}
	public void setInstall(int install) {
		this.install = install;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
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
		if (userPic.indexOf(PropUtil.PIC_HTTPIP)>-1){
			return userPic;
		}else{
			return PropUtil.PIC_HTTPIP+userPic;
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
	
	//RuleBasedCollator collator = (RuleBasedCollator)Collator.getInstance(Locale.CHINA);
	/*@Override
	public int compareTo(User u) {
		
		String thisZ=this.shouZiMu;
		if(thisZ==null) {
			thisZ="";
		}else {
			thisZ=this.shouZiMu.toLowerCase();
		}
		String paraZ=u.getShouZiMu();
		if(paraZ==null) {
			paraZ="";
		}else {
			paraZ=u.getShouZiMu().toLowerCase();
		}
		
		if ((thisZ.compareTo(paraZ)) > 0) {
			return 1; // 正整数是大于
		} else if ((thisZ.compareTo(paraZ)) < 0) {
			return -1;// 负整数是小于
		} else {
			return 0; // 0为等于
		}
	}*/
	
	public static void main(String[] args) {
		
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
	
}
