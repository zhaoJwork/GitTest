package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */
@Data
@ApiModel(value="InExitGroup", description="退出群组")
public class InExitGroup {

    @ApiModelProperty(value = "操作人staffid")
    private String customerId ;
    @ApiModelProperty(value = "群ID")
    private String groupId;

}
