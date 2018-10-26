package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */
@Data
@ApiModel(value="InUpdateGroupInfo", description="群头像更新")
public class InUpdateGroupInfo {

    @ApiModelProperty(value = "操作人staffid")
    private String masterId ;
    @ApiModelProperty(value = "群ID")
    private String groupId;
    @ApiModelProperty(value = "群头像")
    private String avatar;
}
