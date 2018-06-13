package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能概要：UserService接口类
 * 
 * @author 
 * @since   
 */
@Service("userService1")
public class UserService extends AbstractService<User,String> {

	@Autowired
	public UserService(UserRepository userRepository){
		super(userRepository);
	}
	@Override
	public long deleteByIds(String... strings) {
		return 0;
	}

	@Override
	public List<User> findByIds(String... strings) {
		QUser user = QUser.user;
		JPAQueryFactory query = jpaQueryFactory();

		return query.select(user).from(user).where(user.userID.in(strings)).fetch();
	}

	/**
	 * 根据人员ID查询个人详情
	 * @param userID
	 * @return
	 */
	public UserDetailsDsl selectUserDetails(String userID){
		QUser user = QUser.user;
		QUserNewAssistDsl uass = QUserNewAssistDsl.userNewAssistDsl;
		return jpaQueryFactory().select(Projections.bean(UserDetailsDsl.class,
				user.userID,
				user.userName,
				new CaseBuilder().when(uass.portrait_url.eq("").or(uass.portrait_url.isNull())).then(user.userPic).otherwise(uass.portrait_url).as("userPic")

		)).from(user)
				.leftJoin(uass)
				.on(user.userID.eq(uass.userid))
				.where(user.userID.eq(userID)).fetchOne();
	}
}
