package com.lin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import com.lin.core.redis.RedisService;
import com.lin.domain.User;
import com.lin.service.UserService;

/**
 * 功能概要：UserController
 * 
 * @author 
 * @since  
 */
@Controller
public class UserController {

	@Resource
	private UserService userService;


	@RequestMapping("/index")
	public ModelAndView getIndex() {
		//mysql
		ModelAndView mav = new ModelAndView("index");
		User user = userService.selectUserById(1);
		System.out.println(user.getUserName() + "" + user.getUserPassword() + "" + user.getUserEmail());

		mav.addObject("user", user);
		return mav;
	}
}