package com.lin.connnector;


import com.ideal.connect.Connector;
import com.lin.connnector.crowd.QueryGroup;
import com.lin.connnector.crowd.QueryGroupByID;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 三部群组http请求
 * @author lwz
 * @date 2018.12.7
 */
public interface CrowdConnector extends Connector {

    /**
     * 查询群组列表
     * @param customerId
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("POST /enterprise_go/group/queryGroup")
    QueryGroup queryGroup(@Param("customerId")String customerId);

    /**
     * 根据群组ID获取群成员列表
     * @param customerId
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("POST /enterprise_go/group/queryGroupById")
    QueryGroupByID queryGroupById(@Param("customerId")String customerId,@Param("groupId")String groupId);

}
