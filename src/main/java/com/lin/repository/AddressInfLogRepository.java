package com.lin.repository;

import com.lin.domain.AddressInfLogBean;
import com.lin.domain.OrganizationBlackDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 日志
 * @author lwz
 * @date 2018.6.11
 */
public interface AddressInfLogRepository extends PagingAndSortingRepository<AddressInfLogBean, String>,QuerydslPredicateExecutor<AddressInfLogRepository> {

}
