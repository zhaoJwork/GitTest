package com.lin.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lin.domain.AddressBanned;

public interface AddressBannedRepository extends PagingAndSortingRepository<AddressBanned, String>, QuerydslPredicateExecutor<AddressBanned> {

}
