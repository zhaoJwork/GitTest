package com.lin.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.OrganizationRepository;
import com.lin.util.JsonUtil;
import com.lin.vo.OutDep;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织机构service 实现类
 * 
 * @author zhangWeiJie
 * @date 2017年8月19日
 */
@Service("organizationServiceDsl")
public class OrganizationService extends AbstractService<OrganizationDsl,String> {
	@Value("${application.redis_host}")
	private String redis_host;
	@Value("${application.redis_port}")
	private int redis_port;
	@Value("${application.redis_timeout}")
	private int redis_timeout;

	public OrganizationService(OrganizationRepository pagingRepository) {
		super(pagingRepository);
	}

	/**
	 * 查询组织机构列表集
	 * @param organ
	 * @return
	 */
	public List<OrganizationDsl> getOrganizationByDsl(OrganizationDsl organ) {
        QOrganizationDsl organDsl = QOrganizationDsl.organizationDsl;
		QOrganizationDsl organs = QOrganizationDsl.organizationDsl;
        QOrganizationBlack blackDsl = QOrganizationBlack.organizationBlack;

		QOrganizationIndex qOrganizationIndex = QOrganizationIndex.organizationIndex;
		Predicate predicate = null;
		JPAQueryFactory queryFactory = jpaQueryFactory();
		if(null != organ.getpID()&& !organ.getpID().equals("")){
			predicate = QOrganizationDsl.organizationDsl.pID.eq(organ.getpID());
			List<OrganizationDsl> list = new ArrayList<OrganizationDsl>();
			List<OrganizationDsl> retList = new ArrayList<OrganizationDsl>();
			list = queryFactory.select(
					Projections.bean(OrganizationDsl.class,
							organDsl.rowID,
							organDsl.organizationID,
							organDsl.organizationName,
							organDsl.pID,
							organDsl.type,
							organDsl.updateDate,
							organDsl.orderValue,
							organDsl.flag,
							organDsl.onlineCount,
							organDsl.userCount,
							organDsl.zimuname,
							organDsl.markID.as("markID"),
							new CaseBuilder().when(organDsl.markID.eq("1")).then("营销拓展")
									.when(organDsl.markID.eq("2")).then("能力支撑")
									.when(organDsl.markID.eq("3")).then("战略统筹")
									.otherwise("").as("markName"),
							new CaseBuilder().when(organDsl.markID.eq("")).then(0).otherwise(0).as("markOnline"),
							new CaseBuilder().when(organDsl.markID.eq("")).then(0).otherwise(0).as("markUser"),
							qOrganizationIndex.orgIDIndex.as("orgIDIndex"),
							qOrganizationIndex.orgNameIndex.as("orgNameIndex")
							)
			).from(organDsl)
					.leftJoin(qOrganizationIndex)
					.on(qOrganizationIndex.orgID.eq(organDsl.organizationID))
					.where(predicate)
					.where(QOrganizationDsl.organizationDsl.organizationID.notIn(queryFactory.select(blackDsl.organizationID).from(blackDsl)))
					.orderBy(QOrganizationDsl.organizationDsl.markID.asc())
					.orderBy(QOrganizationDsl.organizationDsl.orderValue.asc()).fetch();

			if(0 < list.size()){
				List noTalk = entityManager.createNativeQuery("select ot.mark ,sum(ot.organ_user_count), sum(ot.organ_online_count)" +
						"  from appuser.address_organization ot" +
						" where ot.pid = ?" +
						" group by ot.mark")
						.setParameter(1,organ.getpID())
						.getResultList();

				if(0 < noTalk.size()){
					for(OrganizationDsl ret :list)
					{
						for(int i = 0 ; i < noTalk.size() ; i ++){
							Object[] obj = (Object[])noTalk.get(i);
							if(obj[0].equals(ret.getMarkID())){
								ret.setMarkOnline(Integer.parseInt(obj[2].toString()));
								ret.setMarkUser(Integer.parseInt(obj[1].toString()));
							}
						}
						retList.add(ret);
					}
					return retList;
				}
				return list;
			}
		}
		if(null != organ.getOrganizationID() && !organ.getOrganizationID().equals("")){
			predicate = QOrganizationDsl.organizationDsl.organizationID.eq(organ.getOrganizationID());
			return queryFactory.select(Projections.bean(OrganizationDsl.class,
					organDsl.rowID,
					organDsl.organizationID,
					organDsl.organizationName,
					organDsl.pID,
					organDsl.type,
					organDsl.updateDate,
					organDsl.orderValue,
					organDsl.flag,
					organDsl.onlineCount,
					organDsl.userCount,
					organDsl.zimuname,
					organDsl.markID.as("markID"),
					new CaseBuilder().when(organDsl.markID.eq("1")).then("营销")
							.when(organDsl.markID.eq("2")).then("支撑")
							.when(organDsl.markID.eq("3")).then("综合")
							.otherwise("").as("markName"),
					qOrganizationIndex.orgIDIndex.as("orgIDIndex"),
					qOrganizationIndex.orgNameIndex.as("orgNameIndex")
			)).from(organDsl)
					.leftJoin(qOrganizationIndex)
					.on(qOrganizationIndex.orgID.eq(organDsl.organizationID))
					.where(predicate)
					.orderBy(QOrganizationDsl.organizationDsl.markID.asc())
					.fetch();
		}
		return queryFactory.select(Projections.bean(OrganizationDsl.class,
				organDsl.rowID,
				organDsl.organizationID,
				organDsl.organizationName,
				organDsl.pID,
				organDsl.type,
				organDsl.updateDate,
				organDsl.orderValue,
				organDsl.flag,
				organDsl.onlineCount,
				organDsl.userCount,
				organDsl.zimuname,
				organDsl.markID.as("markID"),
				new CaseBuilder().when(organDsl.markID.eq("1")).then("营销")
						.when(organDsl.markID.eq("2")).then("支撑")
						.when(organDsl.markID.eq("3")).then("综合")
						.otherwise("").as("markName"),
				qOrganizationIndex.orgIDIndex.as("orgIDIndex"),
				qOrganizationIndex.orgNameIndex.as("orgNameIndex")
		)).from(organDsl).leftJoin(qOrganizationIndex)
				.on(qOrganizationIndex.orgID.eq(organDsl.organizationID))
				.where(predicate).orderBy(QOrganizationDsl.organizationDsl.markID.asc()).fetch();
	}

	/**
	 * 获取部门列表
	 * @param provinceID
	 * @return
	 */
	public List<OutDep> getDepList(String provinceID){
		List<OutDep> depList= new ArrayList<OutDep>();
		QOrganizationDsl organs = QOrganizationDsl.organizationDsl;
		QOrganizationBlack blackDsl = QOrganizationBlack.organizationBlack;
		QOrganizationIndex qOrganizationIndex = QOrganizationIndex.organizationIndex;
		JPAQuery jpaQuery = jpaQueryFactory().select(Projections.bean(OutDep.class,
				organs.organizationID.min().as("depID"),
				organs.organizationName.as("depName")
		)).from(organs)
				.where(organs.flag.eq("1"))
				.where(organs.organizationID.notIn(jpaQueryFactory().select(blackDsl.organizationID).from(blackDsl)));
		if(null != provinceID && !"".equals(provinceID)) {
			jpaQuery.where(organs.organizationID.in(jpaQueryFactory().select(qOrganizationIndex.orgID).from(qOrganizationIndex)
			.where(qOrganizationIndex.orgIDIndex.like(provinceID +"%"))));
		}
		jpaQuery.groupBy(organs.organizationName);
		depList = jpaQuery.fetch();
		return depList;
	}


	@Override
	public long deleteByIds(String... strings) {
		return 0;
	}

	@Override
	public List<OrganizationDsl> findByIds(String... strings) {
		QOrganizationDsl organDsl = QOrganizationDsl.organizationDsl;
		JPAQueryFactory query = jpaQueryFactory();

		return query.select(organDsl).from(organDsl).where(organDsl.organizationID.in(strings)).fetch();
	}
}
