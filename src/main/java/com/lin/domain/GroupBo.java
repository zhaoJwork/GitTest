package com.lin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据库分组
 * 
 * @author lwz
 * @date 2018.10.10
 */


@ApiModel(value = "GroupBo", description = "分组")
@Entity
@Data
@Table(name = "address_group")
public class GroupBo {
	@ApiModelProperty(value = "主键")
	@Id
	@Column(name = "ROW_ID")
	private String rowID;
	/**
	 * 分组ID
	 */
	@Column(name = "GROUP_ID")
	private String groupID;
	/**
	 * 分组名称
	 */
	@Column(name = "GROUP_NAME")
	private String groupName;
	/**
	 * 备注，描述
	 */
	@Column(name = "GROUP_DESC")
	private String groupDesc;
	/**
	 * 创建人
	 */
	@Column(name = "CREATE_USER")
	private String createUser;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE")
	private String createDate;
	/**
	 *  最后修改时间
	 */
	@Column(name = "UPDATE_DATE")
	private String updateDate;
	/**
	 * 图片
	 */
	@Column(name = "GROUP_IMG")
	private String groupImg;

}
