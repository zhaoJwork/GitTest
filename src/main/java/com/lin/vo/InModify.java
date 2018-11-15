package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */
@Data
@ApiModel(value="InModify", description="修改群名")
public class InModify {
    @ApiModelProperty(value = "操作人staffid")
    private String customerId ;
    @ApiModelProperty(value = "群ID")
    private String id;
    @ApiModelProperty(value = "群名称")
    private String groupName;
}
