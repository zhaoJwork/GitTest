package com.lin.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.mapper.OrganizationMapper;
import com.lin.repository.OrganizationRepository;
import com.lin.util.JsonUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class OrganizationServiceImpl extends AbstractService<OrganizationDsl,String> {
	@Value("${application.redis_host}")
	private String redis_host;
	@Value("${application.redis_port}")
	private int redis_port;
	@Value("${application.redis_timeout}")
	private int redis_timeout;

	@Autowired
	private OrganizationMapper organizationDao;

	@Autowired
	private OrganizationRepository repository;

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}

	/**
	 * 查询组织机构列表集
	 * @param organ
	 * @return
	 */
	public List<OrganizationDsl> getOrganizationByDsl(OrganizationDsl organ) {
        QOrganizationDsl organDsl = QOrganizationDsl.organizationDsl;
        QOrganizationBlack blackDsl = QOrganizationBlack.organizationBlack;
		QOrganizationIndex qOrganizationIndex = QOrganizationIndex.organizationIndex;
		Predicate predicate = null;
		if(null != organ.getpID()&& !organ.getpID().equals("")){
			predicate = QOrganizationDsl.organizationDsl.pID.eq(organ.getpID());
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
					qOrganizationIndex.orgIDIneex.as("orgIDIndex"),
					qOrganizationIndex.orgNameIneex.as("orgNameIndex")
					)).from(organDsl)
					.leftJoin(qOrganizationIndex)
					.on(qOrganizationIndex.orgID.eq(organDsl.organizationID))
					.where(predicate)
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
					qOrganizationIndex.orgIDIneex.as("orgIDIndex"),
					qOrganizationIndex.orgNameIneex.as("orgNameIndex")
			)).from(organDsl)
					.leftJoin(qOrganizationIndex)
					.on(qOrganizationIndex.orgID.eq(organDsl.organizationID))
					.where(predicate)
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
				qOrganizationIndex.orgIDIneex.as("orgIDIndex"),
				qOrganizationIndex.orgNameIneex.as("orgNameIndex")
		)).from(organDsl)
				.leftJoin(qOrganizationIndex)
				.on(qOrganizationIndex.orgID.eq(organDsl.organizationID)).where(predicate).fetch();
	}

	@Autowired
	public OrganizationServiceImpl(OrganizationRepository repository){
		super(repository);
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

public void rmJedisOrg(){
	List<OrganizationBean> organizationList = organizationDao.fiveCityOrganization("123456");
	Map<String, String> map = new LinkedHashMap<String, String>();
	for (OrganizationBean o : organizationList) {
		try {
			map.put(o.getOrganizationID(), JsonUtil.toJson(o));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	Jedis jedis = new Jedis(redis_host, redis_port,redis_timeout);
	jedis.set("fiveOrgText", JSON.toJSONString(map,true));
}

}
