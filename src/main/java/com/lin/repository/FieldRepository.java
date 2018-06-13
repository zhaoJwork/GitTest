package com.lin.repository;

import com.lin.domain.FieldDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FieldRepository extends PagingAndSortingRepository<FieldDsl, String>,QuerydslPredicateExecutor<FieldDsl> {

}

