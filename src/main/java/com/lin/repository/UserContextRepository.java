package com.lin.repository;

import com.lin.domain.UserContext;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserContextRepository extends PagingAndSortingRepository<UserContext, String>,QuerydslPredicateExecutor<UserContext
        > {

}
