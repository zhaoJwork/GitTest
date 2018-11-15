package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */
@Data
@ApiModel(value="InMembers", description="群成员列表")
public class InMembers {

    @ApiModelProperty(value = "邀请群成员")
    private String customerId;
    @ApiModelProperty(value = "群ID")
    private String groupId;
}
