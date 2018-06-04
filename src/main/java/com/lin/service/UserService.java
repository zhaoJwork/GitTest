package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.QUser;
import com.lin.domain.User;
import com.lin.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
