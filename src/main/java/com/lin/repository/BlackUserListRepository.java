package com.lin.repository;

import com.lin.domain.BlackUserList;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlackUserListRepository extends PagingAndSortingRepository<BlackUserList, String>,QuerydslPredicateExecutor<BlackUserList> {

}
