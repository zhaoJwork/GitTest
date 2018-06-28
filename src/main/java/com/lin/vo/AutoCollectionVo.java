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



	

	
	
}
