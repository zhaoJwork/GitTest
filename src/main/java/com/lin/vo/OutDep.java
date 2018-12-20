package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通讯录部门列表
 * @author lwz
 * @date 2018.12.3
 */
@ApiModel(value="OutUser", description="通讯录部门列表")
@Data
public class OutDep {
    @ApiModelProperty(value = "用户id")
    private String depID;
    @ApiModelProperty(value = "用户名称")
    private String depName;

}
