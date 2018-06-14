package com.lin.repository;

import com.lin.domain.UserFieldDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserFieldRepository extends PagingAndSortingRepository<UserFieldDsl, String>,QuerydslPredicateExecutor<UserFieldDsl> {

}
