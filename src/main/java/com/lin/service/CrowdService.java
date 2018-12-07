package com.lin.service;

import com.ideal.connect.FeignFactory;
import com.lin.connnector.CrowdConnector;
import com.lin.connnector.crowd.QueryGroup;
import com.lin.connnector.crowd.QueryGroupByID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 群service
 * @author  lwz
 * @date 2018.12.7
 */
@Service
public class CrowdService {
    @Autowired
    private FeignFactory feignFactory;
    /**
     * 查询指定人群组
     * @param userID
     */
    public QueryGroup queryGroup(String userID)  {
        return feignFactory.builder().getConnector(CrowdConnector.class)
                .queryGroup(userID);
    }

    /**
     * 根据群组ID获取群成员列表
     * @param userID
     * @param groupID
     */
    public QueryGroupByID queryGroupById(String userID,String groupID)  {
        return feignFactory.builder().getConnector(CrowdConnector.class)
                .queryGroupById(userID,groupID);
    }
}
