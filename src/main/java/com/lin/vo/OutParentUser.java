package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 通讯录人员列表父节点
 * @author lwz
 * @date 2018.12.3
 */
@ApiModel(value="OutUser", description="通讯录人员列表返回详情")
@Data
public class OutParentUser {
    @ApiModelProperty(value = "用户数量")
    private long userCount;
    @ApiModelProperty(value = "用户列表")
    private List<OutUser> outUserList;

}
