package com.lin.repository;

import com.lin.domain.AddressColAuxiliary;
import com.lin.domain.AddressCollection;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressColAuxiliaryRepository extends PagingAndSortingRepository<AddressColAuxiliary, String>, QuerydslPredicateExecutor<AddressColAuxiliary> {

}
