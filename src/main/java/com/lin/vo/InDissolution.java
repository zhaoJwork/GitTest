package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */
@Data
@ApiModel(value="InDissolution", description="解散群组")
public class InDissolution {

    @ApiModelProperty(value = "操作人staffid")
    private String masterId ;
    @ApiModelProperty(value = "群ID")
    private String groupId;

}
