package com.lin.service;

import java.util.List;
import java.util.Map;

import com.lin.domain.OrgTer;
import com.lin.domain.Organization;

/**
 * 组织机构service
 * @author zhangWeiJie
 * @date 2017年8月19日
 */
public interface OrganizationServiceI {

	Map<String, Object> getSyncOrganization(String orgtreeDate,String organizationID);

	/**
	 * 省份和领域
	 * @author hzh
	 * @time 2017-08-24
	 * @return
	 */
	Map getOrgName();

	/**
	 * 根据ID查询实体
	 * @param organid
	 * @return
	 */
	Organization getOrganizationByOrganid(String organid);
	
	/**
	 * 获取50地市组织 
	 * @param loginID
	 * @return
	 */
	Map<String, Object> fiveCityOrganization(String loginID);
	/**
	 * 积分
	 * @param map
	 * @return
	 */
	int getCountIntegralByDay(Map<String, String> map);
}
