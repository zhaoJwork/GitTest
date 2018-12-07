package com.lin.connnector.crowd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *  查询群组列表
 *  请求三部post json 格式
 */
@Data
public class QueryGroupByID {
    @ApiModelProperty("状态编码")
    private String respCode;
    @ApiModelProperty("状态")
    private String respMsg;
    @ApiModelProperty("群列表")
    private DateBean data;
    @Data
    private class DateBean{
        @ApiModelProperty("群ID")
        private String groupId;
        @ApiModelProperty("群名称")
        private String groupName;
        @ApiModelProperty("群头像")
        private String avatar;
        @ApiModelProperty("群人数")
        private int membersNum;
        @ApiModelProperty("群人员列表")
        private List<CustOmerList> customerList;
    }
    @Data
    private static class CustOmerList{
        @ApiModelProperty("人员ID")
        private String customerId;
        @ApiModelProperty("人员名称")
        private String nickName;
        @ApiModelProperty("群头像")
        private String avatar;

    }
}
