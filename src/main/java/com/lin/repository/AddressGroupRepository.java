package com.lin.repository;

import com.lin.domain.AddressGroup;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressGroupRepository extends PagingAndSortingRepository<AddressGroup, String>, QuerydslPredicateExecutor<AddressGroup> {

}
