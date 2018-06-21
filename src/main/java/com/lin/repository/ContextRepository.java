package com.lin.repository;

import com.lin.domain.Context;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContextRepository extends PagingAndSortingRepository<Context, String>,QuerydslPredicateExecutor<Context> {

}
