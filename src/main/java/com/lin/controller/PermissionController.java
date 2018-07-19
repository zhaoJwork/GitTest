package com.lin.controller;

import javax.servlet.http.HttpServletRequest;

import com.lin.vo.AddressCollectionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.domain.AddressInfLogBean;
import com.lin.service.AddressInfLogServiceI;
import com.lin.service.PermissionService;
import com.lin.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 
 * 通讯录
 * @author liudongdong
 * @date 2018年6年6月
 *
 */
@Api(description = "收藏权限API")
@RequestMapping("/permission")
@RestController
public class PermissionController {
	
	@Autowired
	private AddressInfLogServiceI logService;
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * @param req
	 *  	  loginId 当前登入人id
	 * 		  type 查看类型权限
	 * @return Result 0 无权限 1有权限
	 * @author liudongdong
	 * @date 2018年6月6号
	 * @describe 获取禁言权限查询
	 * 			
	 */
	@ApiOperation(value="禁言权限查询")
	@ApiImplicitParams({
	      @ApiImplicitParam(name = "loginId", value = "当前登入Id", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "type", value = "类型权限", required = true, dataType = "Integer")
	  })
	@GetMapping("/bannedsaycheck")
	public Result getBannedSayCheck(HttpServletRequest req, String loginId, Integer type) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "禁言权限查询");
		Result result = new Result();
		if(loginId == null || loginId.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(type == null) {
			result.setRespCode("2");
			result.setRespDesc("type 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		
		try {
			this.permissionService.getBannedSayCheck(loginId, type,result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}
	
	
	/**
	 * @param req
	 *  	  loginId 当前登入人id
	 * 		  type 查看类型权限
	 * @return Result 0 无权限 1有权限
	 * @author liudongdong
	 * @date 2018年6月6号
	 * @describe 获取禁言权限查询
	 * 			
	 */
	@ApiOperation(value="当前人是否已经被禁言查询")
	@ApiImplicitParam(name = "loginId", value = "当前登入Id", required = true, dataType = "String")
	@GetMapping("/isbannedsay")
	public Result getIsBannedSay(HttpServletRequest req, String loginId) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "当前人是否已经被禁言查询");
		Result result = new Result();
		if(loginId == null || loginId.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		
		try {
			this.permissionService.getIsBannedSay(loginId, result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}
	
	
	/**
	 * @param req
	 *  	  loginId 当前登入人id
	 * 		  type 查看类型权限
	 *  	  userId  被查看人id
	 * @return Result 0 无权限 1有权限
	 * @author liudongdong
	 * @date 2018年6月6号
	 * @describe 获取禁言权限查询和指数权限查询
	 * 			
	 */
	@ApiOperation(value="能力指数权限查询")
	@ApiImplicitParams({
	      @ApiImplicitParam(name = "loginId", value = "当前登入Id", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "type", value = "类型权限 2 能力指数", required = true, dataType = "int"),
	      @ApiImplicitParam(name = "userId", value = "被查看Id", required = true, dataType = "String")
	  })
	@GetMapping("/abilitycheck")
	public Result getAbilitycheck(HttpServletRequest req, String loginId, Integer type, String userId) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "能力指数权限查询");
		Result result = new Result();
		if(loginId == null || loginId.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(type == null) {
			result.setRespCode("2");
			result.setRespDesc("type 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(userId == null) {
			result.setRespCode("2");
			result.setRespDesc("userId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		
		try {
			this.permissionService.getAbilitycheck(loginId, type, userId,result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}
	
	/**
	 * 
	 * @param req
	 * 		  addressBanned 禁言实体类
	 * @return 0操作失败 1操作成功
	 * @author liudongdong
	 * @date 2018年6月6号
	 * @describe 禁言添加
	 * 
	 */
	@ApiOperation(value="添加禁言")
	@ApiImplicitParam(dataType = "AddressBanned")
	@PostMapping("/addbannedsay")
	public Result addBannedSay(HttpServletRequest req,AddressBanned addressBanned) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "添加禁言");
		Result result = new Result();
		if(addressBanned == null) {
			result.setRespCode("2");
			result.setRespDesc("addressBanned 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getBannedSayLoginId()==null) {
			result.setRespCode("2");
			result.setRespDesc("bannedSayLoginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getBannedSayUserId()==null) {
			result.setRespCode("2");
			result.setRespDesc("bannedSayUserId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getType()==null) {
			result.setRespCode("2");
			result.setRespDesc("type 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getBannedSayType()==null) {
			result.setRespCode("2");
			result.setRespDesc("bannedSayType 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		
		try {
			
			this.permissionService.addBannedSay(addressBanned, result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}
	
	/**
	 * 
	 * @param req
	 * 		  addressBanned 禁言实体类
	 * @return 0操作失败 1操作成功
	 * @author liudongdong
	 * @date 2018年6月6号
	 * @describe 取消禁言
	 * 
	 */
	@ApiOperation(value="取消禁言")
	@ApiImplicitParam(dataType = "AddressBanned")
	@PostMapping("/cancelbannedsay")
	public Result cacelBannedSay(HttpServletRequest req, AddressBanned addressBanned, BindingResult bindingResult) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "取消禁言");
		Result result = new Result();
		if(addressBanned == null) {
			result.setRespCode("2");
			result.setRespDesc("addressBanned 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getBannedSayLoginId()==null) {
			result.setRespCode("2");
			result.setRespDesc("bannedSayLoginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getBannedSayUserId()==null) {
			result.setRespCode("2");
			result.setRespDesc("bannedSayUserId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getType()==null) {
			result.setRespCode("2");
			result.setRespDesc("type 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressBanned.getBannedSayType()==null) {
			result.setRespCode("2");
			result.setRespDesc("bannedSayType 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		
		try {
			
			this.permissionService.cancelBannedSay(addressBanned, result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}

	/**
	 * 添加收藏
	 * @param req
	 * 		  addressCollection 收藏实体类
	 * @return 0 操作失败 1 操作成功
	 * @author liudongdong
	 * @date 2018年6月7日
	 * @describe 添加收藏
	 */
	@ApiOperation(value="添加收藏")
	@ApiImplicitParam(dataType = "AddressCollection")
	@GetMapping("/addcollection")
	public Result addCollection(HttpServletRequest req, AddressCollectionVo addressCollection) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "添加收藏");
		Result result = new Result();
		if(addressCollection == null) {
			result.setRespCode("2");
			result.setRespDesc("addressCollection 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getCollectionLoginId() == null) {
			result.setRespCode("2");
			result.setRespDesc("collectionLoginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getCollectionUserId() == null) {
			result.setRespCode("2");
			result.setRespDesc("collectionUserId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getType() == null) {
			result.setRespCode("2");
			result.setRespDesc("type 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getCollectionType() == null) {
			result.setRespCode("2");
			result.setRespDesc("collectionType 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(null == addressCollection.getSource()) {
			result.setRespCode("2");
			result.setRespDesc("source 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(null == addressCollection.getQuanPin()) {
			result.setRespCode("2");
			result.setRespDesc("quanPin 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(null == addressCollection.getShouZiMu()) {
			result.setRespCode("2");
			result.setRespDesc("shouZiMu 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		try {
			this.permissionService.addAddressCollection(addressCollection, result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}
	
	/**
	 * 取消收藏
	 * @param req
	 * 		  addressCollection 收藏实体类
	 * @return 0 操作失败 1 操作成功
	 * @author liudongdong
	 * @date 2018年6月7日
	 * @describe 取消收藏
	 */
	@ApiOperation(value="取消收藏")
	@ApiImplicitParam(dataType = "AddressCollection")
	@PostMapping("/cancelcollection")
	public Result cancelCollection(HttpServletRequest req, AddressCollection addressCollection) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "取消收藏");
		Result result = new Result();
		if(addressCollection == null) {
			result.setRespCode("2");
			result.setRespDesc("addressCollection 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getCollectionLoginId() == null) {
			result.setRespCode("2");
			result.setRespDesc("collectionLoginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getCollectionUserId() == null) {
			result.setRespCode("2");
			result.setRespDesc("collectionUserId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getType() == null) {
			result.setRespCode("2");
			result.setRespDesc("type 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if(addressCollection.getCollectionType() == null) {
			result.setRespCode("2");
			result.setRespDesc("collectionType 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}

		try {
			this.permissionService.cancelAddressCollection(addressCollection, result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}
	
	/**
	 * 收藏列表查询
	 * @param req
	 * 		  loginId 当前登入人
	 * @return 0 操作失败 1 操作成功
	 * @author liudongdong
	 * @date 2018年6月13日
	 * @describe 取消收藏
	 */
	@ApiOperation(value="收藏列表查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "loginId", required = true, dataType = "String"),
		@ApiImplicitParam(name = "search", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageNum", required = true, dataType = "String")
	})
	@GetMapping("/collectionlist")
	public Result getCollectionList(HttpServletRequest req,String loginId,String search, String pageSize, String pageNum) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "收藏列表查询");
		Result result = new Result();
		if(loginId == null) {
			result.setRespCode("2");
			result.setRespDesc("loginId 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		try {
			this.permissionService.getCollectionList(loginId,search, pageSize, pageNum, result);
			logService.saveAddressInfLog(log, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
			logService.saveAddressInfLog(log, result);
		}
		return result;
	}
	
}
