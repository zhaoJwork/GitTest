package com.lin.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressGroupRepository extends PagingAndSortingRepository<AddressGroupRepository, String>, QuerydslPredicateExecutor<AddressGroupRepository> {

}
