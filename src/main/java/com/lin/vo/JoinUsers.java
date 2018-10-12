package com.lin.vo;

import lombok.Data;

/**
 * 极光推送创建人列表
 */
@Data
public class JoinUsers {

    /**
     * 图片地址
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 人员id
     */
    private String customerId;
    /**
     * 群组ID
     */
    private String groupId;
}
