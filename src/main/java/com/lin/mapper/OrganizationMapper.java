package com.lin.mapper;

import java.util.List;
import java.util.Map;

import com.lin.domain.OrgTer;
import com.lin.domain.OrganizationBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月19日
 */
@Mapper
public interface OrganizationMapper {

	List<OrganizationBean> getNewDateByUpdateTime(String orgtreeDate);

	List<OrgTer> selectOrgName();

	List<OrgTer> selectTerName();
	
	OrganizationBean getOrganizationByOrganid(String organid);

	List<OrganizationBean> fiveCityOrganization(String loginID);
	/**
	 * 根据pid获取部门
	 * @param pid
	 * @return
	 */
	List<OrganizationBean> organizationByPID(String pid);
	/**
	 * 根据organid递归获取下属所有部门
	 * @param organid
	 * @return
	 */
	List<OrganizationBean> downOrganization(String organid);
	/**
	 * 积分
	 * @param organid
	 * @return
	 */
	int getCountIntegralByDay(Map<String, String> map);
}
