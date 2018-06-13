package com.lin.repository;

import com.lin.domain.UesrFieldDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserFieldRepository extends PagingAndSortingRepository<UesrFieldDsl, String>,QuerydslPredicateExecutor<UesrFieldDsl> {

}
