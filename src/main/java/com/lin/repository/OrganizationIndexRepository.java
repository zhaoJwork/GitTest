package com.lin.repository;

import com.lin.domain.OrganizationIndex;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganizationIndexRepository extends PagingAndSortingRepository<OrganizationIndex, String>,QuerydslPredicateExecutor<OrganizationIndex> {

}
