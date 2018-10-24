package com.lin.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lin.domain.BlackUserList;

public interface AddressBlackListRepository extends PagingAndSortingRepository<BlackUserList, String>, QuerydslPredicateExecutor<BlackUserList> {

}
