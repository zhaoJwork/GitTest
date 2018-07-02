package com.lin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lin.domain.AddressInfLog;
import com.lin.domain.AddressInfLogBean;
import com.lin.service.AddressInfLogServiceImpl;
import com.lin.service.CustomerService;
import com.lin.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 客户通讯录
 * @author liudongdong
 * @date 2018年6月27日
 *
 */
@Api(description = "客户通讯录API")
@RequestMapping("/cust")
@RestController
public class CustomerController {
	
	@Autowired
    private AddressInfLogServiceImpl logServiceDsl;
	
	@Autowired
	private CustomerService customerService;

	@ApiOperation(value="通讯录人员列表",tags = {"1s"})
	@ApiImplicitParams({
	      @ApiImplicitParam(name = "loginId", value = "当前登入Id", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "search", value = "模糊搜索关键字", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "custID", value = "客户id多个值以;号隔开", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "pageSize", value = "条数", required = true, dataType = "String"),
	      @ApiImplicitParam(name = "pageNum", value = "页数", required = true, dataType = "String")
	  })
	@GetMapping("/contactlist")
	public Result contactList(HttpServletRequest req, String loginId, String search, String custID, 
			String pageSize, String pageNum) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"通讯录人员列表");
		Result result = new Result();
		if(loginId == null || loginId.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginId 不能为空");
			logServiceDsl.saveAddressInfLog(log, result);
			return result;
		}
		
		try {
			this.customerService.getContactList(loginId, search, custID, pageSize, pageNum, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		return result;
	}
	
	
	@ApiOperation(value="通讯录人员列表",tags = {"2s"})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "loginId", value = "当前登入Id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "search", value = "模糊搜索关键字", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageSize", value = "条数", required = true, dataType = "String"),
		@ApiImplicitParam(name = "pageNum", value = "页数", required = true, dataType = "String")
	})
	@GetMapping("/custlist")
	public Result custList(HttpServletRequest req, String loginId, String search, 
			String pageSize, String pageNum) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"通讯录人员列表");
		Result result = new Result();
		if(loginId == null || loginId.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginId 不能为空");
			logServiceDsl.saveAddressInfLog(log, result);
			return result;
		}
		
		try {
			this.customerService.getCustList(loginId, search, pageSize, pageNum, result);
			
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
