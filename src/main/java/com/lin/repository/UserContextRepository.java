package com.lin.repository;

import com.lin.domain.UserContextDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserContextRepository extends PagingAndSortingRepository<UserContextDsl, String>,QuerydslPredicateExecutor<UserContextDsl
        > {

}
