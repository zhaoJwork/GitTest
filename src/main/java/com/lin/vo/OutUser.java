package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * 通讯录人员列表返回详情
 * @author lwz
 * @date 2018.12.3
 */
@ApiModel(value="OutUser", description="通讯录人员列表返回详情")
@Data
public class OutUser{
    @ApiModelProperty(value = "用户id")
    private String userID;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "crm用户账号")
    private String crmAccount;
    @ApiModelProperty(value = "用户手机号")
    private String phone;
    @ApiModelProperty(value = "用户email")
    private String email;
    @ApiModelProperty(value = "部门id")
    private String depID;
    @ApiModelProperty(value = "部门名称")
    private String depName;
    @ApiModelProperty(value = "省份id")
    private String provinceID;
    @ApiModelProperty(value = "省份名称")
    private String provinceName;
    @ApiModelProperty(value = "组织架构")
    private String address;
    @ApiModelProperty(value = "用户头像")
    private String userImg;
    @ApiModelProperty(value = "上线时间")
    private String loginTime;

}
