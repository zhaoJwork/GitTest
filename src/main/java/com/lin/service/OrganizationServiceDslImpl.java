package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.OrganizationDsl;
import com.lin.domain.QOrganizationBlackDsl;
import com.lin.domain.QOrganizationDsl;
import com.lin.repository.OrganizationRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 组织机构service 实现类
 * 
 * @author zhangWeiJie
 * @date 2017年8月19日
 */
@Service("organizationServiceDsl")
public class OrganizationServiceDslImpl extends AbstractService<OrganizationDsl,String> {

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
        QOrganizationBlackDsl blackDsl = QOrganizationBlackDsl.organizationBlackDsl;
		Predicate predicate = null;
		if(null != organ.getpID()&& !organ.getpID().equals("")){
			predicate = QOrganizationDsl.organizationDsl.pID.eq(organ.getpID());
			return queryFactory.select(organDsl).from(organDsl).where(predicate)
					.where(QOrganizationDsl.organizationDsl.organizationID.notIn(queryFactory.select(blackDsl.organizationID).from(blackDsl)))
					.orderBy(QOrganizationDsl.organizationDsl.orderValue.asc()).fetch();
		}
		if(null != organ.getOrganizationID() && !organ.getOrganizationID().equals("")){
			predicate = QOrganizationDsl.organizationDsl.organizationID.eq(organ.getOrganizationID());
			return queryFactory.select(organDsl).from(organDsl).where(predicate)
					.fetch();
		}
		return queryFactory.selectFrom(organDsl).from(organDsl).where(predicate).fetch();
	}

	@Autowired
	public OrganizationServiceDslImpl(OrganizationRepository repository){
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
}
