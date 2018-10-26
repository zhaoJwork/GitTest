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
@ApiModel(value="InRemoveMembers", description="删除成员")
public class InRemoveMembers {
    @ApiModelProperty(value = "操作人staffId")
    private String masterId;
    @ApiModelProperty(value = "群ID")
    private String groupId;
    @ApiModelProperty(value = "删除成员staffID,多个以逗号分割")
    private String customerIds;
}
