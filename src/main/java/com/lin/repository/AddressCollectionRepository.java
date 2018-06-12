package com.lin.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lin.domain.AddressCollection;

public interface AddressCollectionRepository extends PagingAndSortingRepository<AddressCollection, String>, QuerydslPredicateExecutor<AddressCollection> {

}
