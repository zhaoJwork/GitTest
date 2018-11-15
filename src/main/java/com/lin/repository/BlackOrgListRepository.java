package com.lin.repository;

import com.lin.domain.BlackOrgList;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlackOrgListRepository extends PagingAndSortingRepository<BlackOrgList, String>,QuerydslPredicateExecutor<BlackOrgList> {

}
