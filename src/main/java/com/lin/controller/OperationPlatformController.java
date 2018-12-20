package com.lin.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lin.domain.BlackUserList;
import com.lin.service.OperationPlatformService;
import com.lin.util.JsonUtil;
import com.lin.util.Result;
import com.lin.vo.OperationPlatform;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 通讯录-运营平台API
 * @author liudongdong
 * @date 2018年10月15日
 *
 */
@Api(description = "通讯录-运营平台API")
@RequestMapping("/operat")
@RestController
public class OperationPlatformController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OperationPlatformService operationPlatformService;
	
	
	@ApiOperation(value="角色列表模糊查询")
	@ApiImplicitParam(dataType = "OperationPlatform")
	@GetMapping("/selectUserList")
	public Result selectUserList(HttpServletRequest req, OperationPlatform operat) {
		logger.info("角色列表模糊查询::==" + req.getRequestURL() + "?staffID=" + operat.getStaffID() + "&staffName=" + operat.getStaffName()
			+ "&crmAccount=" +operat.getCrmAccount()+ "&telNum=" +operat.getTelNum()+ "&deptID=" +operat.getDeptID()+ "&type=" + operat.getType()
			+ "&pageSize=" +operat.getPageSize()+ "&pageNum=" +operat.getPageNum()+ "&timeType=" +operat.getTimeType());
		Result result = new Result();
		if(operat.getStaffID() == null || operat.getStaffID().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("staffID 不能为空");
			return result;
		}
		if(operat.getPageSize() == null || operat.getPageSize().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("pageSize 不能为空");
			return result;
		}
		if(operat.getPageNum() == null || operat.getPageNum().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("pageNum 不能为空");
			return result;
		}
		
		try {
			this.operationPlatformService.selectUserList(result,operat);
			logger.info("角色列表模糊查询::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("角色列表模糊查询-异常",e.toString());
		}
		return result;
	}

	@ApiOperation(value="角色列表模糊统计")
	@ApiImplicitParam(dataType = "OperationPlatform")
	@GetMapping("/selectUserCount")
	public Result selectUserCount(HttpServletRequest req, OperationPlatform operat) {
		logger.info("角色列表模糊统计::==" + req.getRequestURL() + "?staffID=" + operat.getStaffID() + "&staffName=" + operat.getStaffName()
				+ "&crmAccount=" +operat.getCrmAccount()+ "&telNum=" +operat.getTelNum()+ "&deptID=" +operat.getDeptID()+ "&type=" + operat.getType()
				+ "&pageSize=" +operat.getPageSize()+ "&pageNum=" +operat.getPageNum()+ "&timeType=" +operat.getTimeType());
		Result result = new Result();
		if(operat.getStaffID() == null || operat.getStaffID().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("staffID 不能为空");
			return result;
		}

		try {
			this.operationPlatformService.selectUserCount(result,operat);
			logger.info("角色列表模糊统计::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("角色列表模糊统计-异常",e.toString());
		}
		return result;
	}
	
	
	@ApiOperation(value="用户在线查询")
	@ApiImplicitParam(dataType = "OperationPlatform")
	@GetMapping("/selectislogin")
	public Result selectislogin(HttpServletRequest req, OperationPlatform operat) {
		logger.info("用户在线查询::==" + req.getRequestURL() + "?staffID=" + operat.getStaffID()+ "&deptID=" +operat.getDeptID()
			+ "&pageSize=" +operat.getPageSize()+ "&pageNum=" +operat.getPageNum());
		Result result = new Result();
		if(operat.getStaffID() == null || operat.getStaffID().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("staffID 不能为空");
			return result;
		}
		if(operat.getPageSize() == null || operat.getPageSize().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("pageSize 不能为空");
			return result;
		}
		if(operat.getPageNum() == null || operat.getPageNum().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("pageNum 不能为空");
			return result;
		}
		
		try {
			this.operationPlatformService.selectislogin(result,operat);
			logger.info("用户在线查询::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("用户在线查询-异常",e.toString());
		}
		return result;
	}
	
	
	@ApiOperation(value="添加黑名单")
	@ApiImplicitParam(dataType = "BlackUserList")
	@GetMapping("/addblack")
	public Result addblack(HttpServletRequest req,BlackUserList blackUserList) {
//		BlackUserList blackUserList = new BlackUserList();
//		blackUserList.setCreateBy(createBy);
//		blackUserList.setUserID(userID);
		logger.info("添加黑名单::==" + req.getRequestURL() + "?staffID=" + blackUserList.getCreateBy()+ "&userID=" +blackUserList.getUserID());
		Result result = new Result();
		if(blackUserList.getCreateBy() == null || blackUserList.getCreateBy().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("staffID 不能为空");
			return result;
		}
		if(blackUserList.getUserID() == null || blackUserList.getUserID().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("userID 不能为空");
			return result;
		}
		
		try {
			this.operationPlatformService.addblack(result,blackUserList);
			logger.info("添加黑名单::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("添加黑名单-异常",e.toString());
		}
		return result;
	}
	
	
	@ApiOperation(value="取消黑名单")
	@ApiImplicitParam(dataType = "BlackUserList")
	@GetMapping("/cancelblack")
	public Result cancelblack(HttpServletRequest req, BlackUserList blackUserList) {
		logger.info("取消黑名单::==" + req.getRequestURL() + "?staffID=" + blackUserList.getCreateBy()+ "&userID=" +blackUserList.getUserID());
		Result result = new Result();
		if(blackUserList.getCreateBy() == null || blackUserList.getCreateBy().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("createBy 不能为空");
			return result;
		}
		if(blackUserList.getUserID() == null || blackUserList.getUserID().trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("userID 不能为空");
			return result;
		}
		
		try {
			this.operationPlatformService.cancelblack(result,blackUserList);
			logger.info("取消黑名单::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("取消黑名单-异常",e.toString());
		}
		return result;
	}

}
