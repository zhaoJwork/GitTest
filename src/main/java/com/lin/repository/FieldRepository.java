package com.lin.repository;

import com.lin.domain.Field;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FieldRepository extends PagingAndSortingRepository<Field, String>,QuerydslPredicateExecutor<Field> {

}

