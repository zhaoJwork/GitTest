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
@ApiModel(value="InCreateGroup", description="创建讨论组")
public class InCreateGroup {
    @ApiModelProperty(value = "创建人staffId")
    private String crator;
    @ApiModelProperty(value = "群组名称")
    private String groupName;
    @ApiModelProperty(value = "群组id")
    private String id;
    @ApiModelProperty(value = "群组头像")
    private String avatar;
    @ApiModelProperty(value = "群成员列表")
    private List<InJoinUsers> joinUsers;
}
