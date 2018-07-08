package com.lin.repository;

import com.lin.domain.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String>,QuerydslPredicateExecutor<User> {

}
