package com.lin.repository;

import com.lin.domain.AddressGroupUser;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressGroupUserRepository extends PagingAndSortingRepository<AddressGroupUser, String>, QuerydslPredicateExecutor<AddressGroupUser> {

}
