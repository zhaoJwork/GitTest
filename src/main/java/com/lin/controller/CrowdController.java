package com.lin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideal.wheel.common.Result;
import com.ideal.wheel.common.ResultGenerator;
import com.lin.connnector.crowd.QueryGroup;
import com.lin.connnector.crowd.QueryGroupByID;
import com.lin.domain.AddressInfLog;
import com.lin.service.AddressInfLogServiceImpl;
import com.lin.service.CrowdService;
import com.lin.service.UserService;
import com.lin.vo.OutParentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


/**
 * 
 * 新通讯录-三部群组透传功能
 * @author lwz
 * @date 2018年12月07日
 *
 */
@Api(description = "新通讯录-三部群组透传功能API")
@RequestMapping("/crowd")
@RestController
public class CrowdController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CrowdService crowdService;

	@ApiOperation(value="查询指定人群组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "userID", value = "查询人员ID", required = true, dataType = "String")
	})
	@GetMapping("/queryGroup")
	public Result queryGroup(@NotBlank String loginID,@NotBlank String userID) {
		logger.info("loginId:{}, request body:{}",loginID,"三部群查询指定人群组-userID :"+userID);
		try {
			QueryGroup queryGroup = this.crowdService.queryGroup(userID);
			Result result = ResultGenerator.genSuccessResult(queryGroup);
			logger.info("loginId:{}, request body:{}",loginID,"三部群查询指定人群组 :" + result.toString());
			return result;
		} catch (Exception e) {
			Result result = ResultGenerator.genErrorResult(e.toString());
			logger.error("loginId:{}, request body:{}",loginID,"三部群查询指定人群组 :" + result.toString());
			return result;
		}
	}

	@ApiOperation(value="根据群组ID获取群成员列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "groupID", value = "群组ID", required = true, dataType = "String")
	})
	@GetMapping("/queryGroupById")
	public Result queryGroupById(@NotBlank String loginID,@NotBlank String groupID) {
		logger.info("loginId:{}, request body:{}",loginID,"三部根据群组ID获取群成员列表-groupID :"+groupID);
		try {
			QueryGroupByID queryGroupById = this.crowdService.queryGroupById(loginID,groupID);
			Result result = ResultGenerator.genSuccessResult(queryGroupById);
			logger.info("loginId:{}, request body:{}",loginID,"三部根据群组ID获取群成员列表 :" + result.toString());
			return result;
		} catch (Exception e) {
			Result result = ResultGenerator.genErrorResult(e.toString());
			logger.error("loginId:{}, request body:{}",loginID,"三部根据群组ID获取群成员列表 :" + result.toString());
			return result;
		}
	}
}
