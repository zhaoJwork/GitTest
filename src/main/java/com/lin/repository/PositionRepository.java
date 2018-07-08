package com.lin.repository;

import com.lin.domain.PositionDsl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PositionRepository extends PagingAndSortingRepository<PositionDsl, String>,QuerydslPredicateExecutor<PositionDsl> {

}
