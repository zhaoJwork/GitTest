package com.lin.repository;

import com.lin.domain.GroupBo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupRepository extends PagingAndSortingRepository<GroupBo, String>,QuerydslPredicateExecutor<GroupBo> {

}
