package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */
@Data
@ApiModel(value="InInviteFriend", description="添加成员")
public class InInviteFriend {
    @ApiModelProperty(value = "操作人staffId")
    private String sender;
    @ApiModelProperty(value = "群成员列表")
    private List<InMembers> members;
}
