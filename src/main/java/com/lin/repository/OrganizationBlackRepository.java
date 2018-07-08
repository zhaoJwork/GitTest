package com.lin.repository;

import com.lin.domain.OrganizationBlack;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 配置组织部门
 * @author lwz
 * @date 2018.6.11
 */
public interface OrganizationBlackRepository extends PagingAndSortingRepository<OrganizationBlack, String>,QuerydslPredicateExecutor<OrganizationBlack> {

}
