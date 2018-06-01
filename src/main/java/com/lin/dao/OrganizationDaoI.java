package com.lin.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lin.domain.OrgTer;
import com.lin.domain.Organization;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月19日
 */
public interface OrganizationDaoI {

	List<Organization> getNewDateByUpdateTime(String orgtreeDate);

	List<OrgTer> selectOrgName();

	List<OrgTer> selectTerName();
	
	Organization getOrganizationByOrganid(String organid);

	List<Organization> fiveCityOrganization(String loginID);
	/**
	 * 根据pid获取部门
	 * @param pid
	 * @return
	 */
	List<Organization> organizationByPID(String pid);
	/**
	 * 根据organid递归获取下属所有部门
	 * @param organid
	 * @return
	 */
	List<Organization> downOrganization(String organid);
	/**
	 * 积分
	 * @param organid
	 * @return
	 */
	int getCountIntegralByDay(Map<String, String> map);
}
