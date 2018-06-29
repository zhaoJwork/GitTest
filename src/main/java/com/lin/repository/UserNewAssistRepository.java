package com.lin.repository;

import com.lin.domain.UserNewAssist;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserNewAssistRepository extends PagingAndSortingRepository<UserNewAssist, String>,QuerydslPredicateExecutor<UserNewAssist> {

}
