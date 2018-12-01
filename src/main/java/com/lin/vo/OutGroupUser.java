package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 分组人员列表
 * @author lwz
 */
@Data
@ApiModel(value="OutGroupUser", description="分组列表")
public class OutGroupUser {
    @ApiModelProperty(value = "用户ID")
    private String userID;
    @ApiModelProperty(value = "用户名称")
    private String userName;

}
