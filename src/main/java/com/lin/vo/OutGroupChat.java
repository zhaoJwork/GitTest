package com.lin.vo;

import lombok.Data;

/**
 * 极光推送分组列表
 */
@Data
public class OutGroupChat {

    /**
     * 图片地址
     */
    private String avatar;

    /**
     * 分组ID
     */
    private String groupId;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 对应群组ID
     */
    private String groupChatID;
}
