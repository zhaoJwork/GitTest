package com.lin.repository;

import com.lin.domain.UserField;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserFieldRepository extends PagingAndSortingRepository<UserField, String>,QuerydslPredicateExecutor<UserField> {

}
