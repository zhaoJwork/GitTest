package com.lin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideal.wheel.common.ResultGenerator;
import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.domain.AddressInfLog;
import com.lin.domain.AddressInfLogBean;
import com.lin.service.AddressInfLogServiceI;
import com.lin.service.AddressInfLogServiceImpl;
import com.lin.service.GroupService;
import com.lin.service.PermissionService;
import com.lin.util.JsonUtil;
import com.lin.util.Result;
import com.lin.vo.AddressCollectionVo;
import com.lin.vo.OutGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 
 * 新通讯录-分组功能
 * @author lwz
 * @date 2018年10月10日
 *
 */
@Api(description = "新通讯录-分组功能API")
@RequestMapping("/group")
@RestController
public class GroupController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private GroupService newGroupService;

	@Autowired
	private AddressInfLogServiceImpl logServiceDsl;

	@ApiOperation(value="角色信息查询")
	@ApiImplicitParams({
	      @ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "queryType", value = "查询类型 1角色", required = true, dataType = "String")
	  })
	@GetMapping("/selectRoleDept")
	public Result selectRoleDept(HttpServletRequest req, String loginID, String queryType) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"角色信息查询接口");
		Result result = new Result();
		if(loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logServiceDsl.saveAddressInfLog(log,result);
			return result;
		}
		if(queryType == null || queryType.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("queryType 不能为空");
			logServiceDsl.saveAddressInfLog(log,result);
			return result;
		}
		
		try {
			this.newGroupService.selectRoleDept(result,loginID,queryType);
			logServiceDsl.saveAddressInfLog(log,result);
		} catch (Exception e) {
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logServiceDsl.saveAddressInfLog(log,result);
		}
		return result;
	}

	@ApiOperation(value="统计当前人数查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "queryType", value = "查询类型 1角色2部门3自定义4已有分组", required = true, dataType = "String"),
			@ApiImplicitParam(name = "roleList", value = "角色列表Josn<list> {\"roleList\":[{\"roleID\":\"1758\"},{\"roleID\":\"2\"}]}", dataType = "String" ),
			@ApiImplicitParam(name = "deptList", value = "部门列表Josn<list> {\"deptList\":[{\"deptID\":\"1844641\"},{\"deptID\":\"2\"}]}", dataType = "String"),
			@ApiImplicitParam(name = "userList", value = "人员列表Josn<list> {\"userList\":[{\"userID\":\"1\"},{\"userID\":\"2\"}]}", dataType = "String"),
			@ApiImplicitParam(name = "groupID", value = "分组ID 分组增加时判断人数", dataType = "String")
	})
	@GetMapping("/selectGroupCount")
	public Result selectGroupCount(HttpServletRequest req, String loginID, String queryType, String roleList, String deptList, String userList, String groupID) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"统计当前人数查询");
		Result result = new Result();
		if(loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logServiceDsl.saveAddressInfLog(log,result);
			return result;
		}
		if(queryType == null || queryType.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("queryType 不能为空");
			logServiceDsl.saveAddressInfLog(log,result);
			return result;
		}

		try {
			this.newGroupService.selectGroupCount(result,loginID,queryType,roleList,deptList,userList,groupID);
			logServiceDsl.saveAddressInfLog(log,result);
		} catch (Exception e) {
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logServiceDsl.saveAddressInfLog(log,result);
		}
		return result;
	}

	@ApiOperation(value="新分组保存功能")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "queryType", value = "查询类型 1角色2部门3自定义4已有分组", required = true, dataType = "String"),
			@ApiImplicitParam(name = "roleList", value = "角色列表Josn<list> {\"roleList\":[{\"roleID\":\"1758\"},{\"roleID\":\"2\"}]}", dataType = "String" ),
			@ApiImplicitParam(name = "deptList", value = "部门列表Josn<list> {\"deptList\":[{\"deptID\":\"1844641\"},{\"deptID\":\"2\"}]}", dataType = "String"),
			@ApiImplicitParam(name = "userList", value = "人员列表Josn<list> {\"userList\":[{\"userID\":\"1\"},{\"userID\":\"2\"}]}", dataType = "String"),
			@ApiImplicitParam(name = "groupID", value = "分组ID", dataType = "String"),
			@ApiImplicitParam(name = "groupName", value = "分组名称", dataType = "String"),
			@ApiImplicitParam(name = "groupDesc", value = "分组详情", dataType = "String")
	})
	@GetMapping("/saveGroupCount")
	public Result saveGroupCount(HttpServletRequest req, String loginID, String queryType, String roleList, String deptList, String userList, String groupID,String groupName,String groupDesc) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"统计当前人数查询");
		Result result = new Result();
		if(loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logger.info("loginID 不能为空");
			logServiceDsl.saveAddressInfLog(log,result);
			return result;
		}
		if(queryType == null || queryType.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("queryType 不能为空");
			logger.info("queryType 不能为空");
			logServiceDsl.saveAddressInfLog(log,result);
			return result;
		}

		try {
			this.newGroupService.saveGroupCount(result,loginID,queryType,roleList,deptList,userList,groupID,groupName,groupDesc);
			logServiceDsl.saveAddressInfLog(log,result);
		} catch (Exception e) {
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logServiceDsl.saveAddressInfLog(log,result);
		}
		return result;
	}


	@ApiOperation(value="根据当前人获取分组列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "groupID", value = "分组ID", dataType = "String"),
		@ApiImplicitParam(name = "groupName", value = "分组名称", dataType = "String")
	})
	@GetMapping("/getGroupListByID")
	public com.ideal.wheel.common.Result getGroupListByID(HttpServletRequest req, String loginID, String groupID, String groupName) throws JsonProcessingException {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"根据当前人获取分组列表");
		if(loginID == null || loginID.trim().equals("")) {
			logger.info("loginID 不能为空");
			return ResultGenerator.genErrorResult("loginID 不能为空");
		}
		try {
			List<OutGroup> result = this.newGroupService.getGroupListByID(loginID,groupID,groupName);
			logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(result));
			return  ResultGenerator.genSuccessResult(result);
		} catch (Exception e) {
			log.setExpError(e.toString());
			com.ideal.wheel.common.Result result = ResultGenerator.genErrorResult(e.toString());
			logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(result));
			e.printStackTrace();
			logger.error("根据当前人获取分组列表：{}",result);
			return result;
		}
	}

	@ApiOperation(value="根据分组ID获取分组详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "groupID", value = "分组ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "数量", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "String")
	})
	@GetMapping("/getGroupDesByID")
	public com.ideal.wheel.common.Result getGroupDesByID(HttpServletRequest req, String loginID, String groupID, String pageSize, String pageNum) throws JsonProcessingException {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"根据分组ID获取分组详情");
		if(loginID == null || loginID.trim().equals("")) {
			logger.info("loginID 不能为空");
			return ResultGenerator.genErrorResult("loginID 不能为空");
		}
		if(groupID == null || groupID.trim().equals("")) {
			logger.info("loginID 不能为空");
			return ResultGenerator.genErrorResult("groupID 不能为空");
		}
		try {
			OutGroup outGroup = this.newGroupService.getGroupDesByID(loginID,groupID,pageSize,pageNum);
			logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(outGroup));
			return  ResultGenerator.genSuccessResult(outGroup);
		} catch (Exception e) {
			log.setExpError(e.toString());
			com.ideal.wheel.common.Result result = ResultGenerator.genErrorResult(e.toString());
			logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(result));
			return result;
		}
	}


}
