package com.lin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分组列表
 * @author lwz
 */
@Data
@ApiModel(value="OutGroup", description="分组列表")
public class OutGroup {
    @ApiModelProperty(value = "分组图片")
    private String groupImg;
    @ApiModelProperty(value = "分组ID")
    private String groupID;
    @ApiModelProperty(value = "分组名称")
    private String groupName;
    @ApiModelProperty(value = "分组人数")
    private long groupUserCount;
    @ApiModelProperty(value = "人员列表")
    private List<OutGroupUser> groupUserList;
}
