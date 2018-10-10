package com.lin.controller;

import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.domain.AddressInfLogBean;
import com.lin.service.AddressInfLogServiceI;
import com.lin.service.GroupService;
import com.lin.service.PermissionService;
import com.lin.util.JsonUtil;
import com.lin.util.Result;
import com.lin.vo.AddressCollectionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * 通讯录-新分组功能
 * @author lwz
 * @date 2018年10月10日
 *
 */
@Api(description = "通讯录-新分组功能API")
@RequestMapping("/group")
@RestController
public class GroupController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GroupService groupService;


	@ApiOperation(value="角色信息查询")
	@ApiImplicitParams({
	      @ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "queryType", value = "查询类型 1角色", required = true, dataType = "String")
	  })
	@GetMapping("/selectRoleDept")
	public Result selectRoleDept(HttpServletRequest req, String loginID, String queryType) {
		logger.info("角色信息查询接口::==" + req.getRequestURL() + "?loginID=" + loginID + "&queryType=" + queryType);
		Result result = new Result();
		if(loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			return result;
		}
		if(queryType == null || queryType.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("queryType 不能为空");
			return result;
		}
		
		try {
			this.groupService.selectRoleDept(result,loginID,queryType);
			logger.info("角色信息查询接口::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("角色信息查询-异常",e.toString());
		}
		return result;
	}

	@ApiOperation(value="统计当前人数查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "queryType", value = "查询类型 1角色2部门3自定义4已有分组", required = true, dataType = "String"),
			@ApiImplicitParam(name = "roleList", value = "角色列表Josn<list> {\"roleList\":\"[{\"roleID\",\"1\"},{\"roleID\",\"2\"}]\"}", dataType = "String" ),
			@ApiImplicitParam(name = "deptList", value = "部门列表Josn<list> {\"deptList\":\"[{\"deptID\",\"1\"},{\"deptID\",\"2\"}]\"}", dataType = "String"),
			@ApiImplicitParam(name = "userList", value = "人员列表Josn<list> {\"userList\":\"[{\"userID\",\"1\"},{\"userID\",\"2\"}]\"}", dataType = "String"),
			@ApiImplicitParam(name = "groupID", value = "分组ID 分组增加时判断人数", dataType = "String")
	})
	@GetMapping("/selectGroupCount")
	public Result selectGroupCount(HttpServletRequest req, String loginID, String queryType, String roleList, String deptList, String userList, String groupID) {
		logger.info("统计当前人数查询接口::==" + req.getRequestURL() + "?loginID=" + loginID + "&queryType=" + queryType+"&roleList=" +roleList+"&deptList=" +deptList+
				"&userList=" +userList+"&groupID=" +groupID);
		Result result = new Result();
		if(loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logger.info("loginID 不能为空");
			return result;
		}
		if(queryType == null || queryType.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("queryType 不能为空");
			logger.info("queryType 不能为空");
			return result;
		}

		try {
			this.groupService.selectGroupCount(result,loginID,queryType,roleList,deptList,userList,groupID);
			logger.info("统计当前人数查询接口::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("统计当前人数查询-异常",e.toString());
		}
		return result;
	}

	@ApiOperation(value="新分组保存功能")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登入Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "queryType", value = "查询类型 1角色2部门3自定义4已有分组", required = true, dataType = "String"),
			@ApiImplicitParam(name = "roleList", value = "角色列表Josn<list> {\"roleList\":\"[{\"roleID\",\"1\"},{\"roleID\",\"2\"}]\"}", dataType = "String" ),
			@ApiImplicitParam(name = "deptList", value = "部门列表Josn<list> {\"deptList\":\"[{\"deptID\",\"1\"},{\"deptID\",\"2\"}]\"}", dataType = "String"),
			@ApiImplicitParam(name = "userList", value = "人员列表Josn<list> {\"userList\":\"[{\"userID\",\"1\"},{\"userID\",\"2\"}]\"}", dataType = "String"),
			@ApiImplicitParam(name = "groupID", value = "分组ID", dataType = "String"),
			@ApiImplicitParam(name = "groupName", value = "分组名称", dataType = "String"),
			@ApiImplicitParam(name = "groupDesc", value = "分组详情", dataType = "String")
	})
	@GetMapping("/saveGroupCount")
	public Result saveGroupCount(HttpServletRequest req, String loginID, String queryType, String roleList, String deptList, String userList, String groupID,String groupName,String groupDesc) {
		logger.info("新分组保存功能接口::==" + req.getRequestURL() + "?loginID=" + loginID + "&queryType=" + queryType+"&roleList=" +roleList+"&deptList=" +deptList+
				"&userList=" +userList+"&groupID=" +groupID+"&groupName=" +groupName+"&groupDesc=" +groupDesc);
		Result result = new Result();
		if(loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logger.info("loginID 不能为空");
			return result;
		}
		if(queryType == null || queryType.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("queryType 不能为空");
			logger.info("queryType 不能为空");
			return result;
		}

		try {
			this.groupService.saveGroupCount(result,loginID,queryType,roleList,deptList,userList,groupID,groupName,groupDesc);
			logger.info("新分组保存功能接口::--" + JsonUtil.toJson(result));
		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logger.error("新分组保存功能-异常",e.toString());
		}
		return result;
	}
	
}
