package com.lin.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ideal.wheel.common.AbstractService;
import com.lin.domain.OrganizationBean;
import com.lin.domain.OrganizationDsl;
import com.lin.domain.QOrganizationBlack;
import com.lin.domain.QOrganizationDsl;
import com.lin.repository.OrganizationRepository;
import com.lin.util.JsonUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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
        QOrganizationBlack blackDsl = QOrganizationBlack.organizationBlack;
		Predicate predicate = null;
		JPAQueryFactory queryFactory = jpaQueryFactory();
		if(null != organ.getpID()&& !organ.getpID().equals("")){
			predicate = QOrganizationDsl.organizationDsl.pID.eq(organ.getpID());
			return queryFactory.select(
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
							new CaseBuilder().when(organDsl.markID.eq("1")).then("营销")
									.when(organDsl.markID.eq("2")).then("支撑")
									.when(organDsl.markID.eq("3")).then("综合")
									.otherwise("").as("markName")
							)
			).from(organDsl).where(predicate)
					.where(QOrganizationDsl.organizationDsl.organizationID.notIn(queryFactory.select(blackDsl.organizationID).from(blackDsl)))
					.orderBy(QOrganizationDsl.organizationDsl.orderValue.asc()).fetch();
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
							.otherwise("").as("markName")
			)).from(organDsl).where(predicate)
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
						.otherwise("").as("markName")
		)).from(organDsl).where(predicate).fetch();
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
