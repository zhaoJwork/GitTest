package com.lin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideal.wheel.common.Result;
import com.ideal.wheel.common.ResultGenerator;
import com.lin.domain.AddressInfLog;
import com.lin.service.AddressInfLogServiceImpl;
import com.lin.service.UserService;
import com.lin.vo.OperationPlatform;
import com.lin.vo.OutUser;
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
import java.util.List;


/**
 * 
 * 通讯录-新分组功能
 * @author lwz
 * @date 2018年12月03日
 *
 */
@Api(description = "新通讯录-用户功能API")
@RequestMapping("/user")
@RestController
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserService userService;

	@Autowired
	private AddressInfLogServiceImpl logServiceDsl;

	@ApiOperation(value="分页获取用户列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "数量", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "userName", value = "姓名后模糊查询", dataType = "String"),
			@ApiImplicitParam(name = "crmAccount", value = "CRM账号后模糊查询", dataType = "String"),
			@ApiImplicitParam(name = "phone", value = "手机号后模糊查询", dataType = "String"),
			@ApiImplicitParam(name = "provinceID", value = "按所属省份精确查询,号分割", dataType = "String"),
			@ApiImplicitParam(name = "depID", value = "按所属部门精确查询,号分割", dataType = "String"),
			@ApiImplicitParam(name = "isLoginTime", value = "是否查询登陆时间1是0否", dataType = "String")


	})
	@GetMapping("/getUserList")
	public Result getUserList(HttpServletRequest req, String loginID, String pageSize,String pageNum,
							  String userName, String crmAccount, String phone, String provinceID, String depID,
							  String isLoginTime
		) throws JsonProcessingException {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"分页获取用户列表");
		if(loginID == null || loginID.trim().equals("")) {
			logger.info("loginID 不能为空");
			return ResultGenerator.genErrorResult("loginID 不能为空");
		}
		if(pageSize == null || pageSize.trim().equals("")) {
			logger.info("pageSize 不能为空");
			return ResultGenerator.genErrorResult("pageSize 不能为空");
		}
		if(pageNum == null || pageNum.trim().equals("")) {
			logger.info("pageNum 不能为空");
			return ResultGenerator.genErrorResult("pageNum 不能为空");
		}

		try {
			List<OutUser> userList = this.userService.getUserList(pageSize,pageNum,
					userName,crmAccount,phone,provinceID,depID,isLoginTime);
			logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(userList));
			return ResultGenerator.genSuccessResult(userList);
		} catch (Exception e) {
			com.ideal.wheel.common.Result result = ResultGenerator.genErrorResult(e.toString());
			logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(result));
			return result;
		}
	}


}
