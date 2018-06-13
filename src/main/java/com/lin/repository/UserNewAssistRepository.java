package com.lin.repository;

import com.lin.domain.UserNewAssistDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserNewAssistRepository extends PagingAndSortingRepository<UserNewAssistDsl, String>,QuerydslPredicateExecutor<UserNewAssistDsl> {

}
