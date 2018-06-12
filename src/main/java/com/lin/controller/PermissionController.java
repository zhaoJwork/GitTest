package com.lin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lin.domain.AddressBanned;
import com.lin.domain.AddressCollection;
import com.lin.domain.AddressInfLogBean;
import com.lin.service.AddressInfLogServiceI;
import com.lin.service.PermissionServiceI;
import com.lin.util.Result;

import lombok.val;

/**
 * 
 * 通讯录
 * @author liudongdong
 * @date 2018年6年6月
 *
 */

@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private AddressInfLogServiceI logService;
	
	@Autowired
	private PermissionServiceI permissionService;
	
	/**
	 * @param req
	 *  	  loginId 当前登入人id
	 * 		  type 查看类型权限
	 *  	  userId  被查看人id
	 * @return Result 0 无权限 1有权限
	 * @author liudongdong
	 * @date 2018年6月6号
	 * @describe 获取禁言权限查询和指数权限查询
	 * 		http://localhost:8008/app-addresslist/addressbook/bannedsay?loginId=22295&type=2
	 * 		返回结果
	 * 		{"respCode":"1","respDesc":"正常返回数据","respMsg":1}
	 * 			
	 */
	@RequestMapping("/bannedsay")
	@ResponseBody
	public Result getBannedSay(HttpServletRequest req, String loginId, Integer type, String userId) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "获取禁言权限查询和指数权限查询");
		Result result = new Result();
		if(loginId == null || loginId.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
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
			this.permissionService.getBannedSay(loginId, type, userId,result);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
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
	@RequestMapping("/addbannedsay")
	@ResponseBody
	public Result addBannedSay(HttpServletRequest req, AddressBanned addressBanned, BindingResult bindingResult) {
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
			
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
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
	@RequestMapping("/cancelbannedsay")
	@ResponseBody
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
			
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		return result;
	}

	/**
	 * 添加收藏或者修改收藏
	 * @param req
	 * 		  addressCollection 收藏实体类
	 * @return 0 操作失败 1 操作成功
	 * @author liudongdong
	 * @date 2018年6月7日
	 * @describe 添加收藏
	 * 
	 * 			修改收藏
	 */
	@RequestMapping("/addresscollection")
	@ResponseBody
	public Result AddCollection(HttpServletRequest req, AddressCollection addressCollection, BindingResult bindingResult) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "添加或修改收藏");
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
			this.permissionService.addressCollection(addressCollection, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		return result;
	}
}
