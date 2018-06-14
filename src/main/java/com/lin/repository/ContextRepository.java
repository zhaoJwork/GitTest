package com.lin.repository;

import com.lin.domain.ContextDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContextRepository extends PagingAndSortingRepository<ContextDsl, String>,QuerydslPredicateExecutor<ContextDsl> {

}
