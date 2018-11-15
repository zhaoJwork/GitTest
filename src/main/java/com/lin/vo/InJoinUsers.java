package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */
@Data
@ApiModel(value="InJoinUsers", description="群成员列表")
public class InJoinUsers {

    @ApiModelProperty(value = "群成员")
    private String customerId;
}
