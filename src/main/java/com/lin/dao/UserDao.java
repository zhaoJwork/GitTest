package com.lin.dao;


import com.lin.domain.User;

/**
 * 功能概要：User的DAO类
 * 
 * @author 
 * @since 
 */
public interface UserDao {
	/**
	 * 
	 * @author 
	 * @since 
	 * @param userId
	 * @return
	 */
	User selectUserById(Integer userId);

}
