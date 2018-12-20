package com.lin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideal.wheel.common.Result;
import com.ideal.wheel.common.ResultGenerator;
import com.lin.domain.AddressInfLog;
import com.lin.domain.OrganizationDsl;
import com.lin.service.AddressInfLogServiceImpl;
import com.lin.service.OrganizationService;
import com.lin.service.UserService;
import com.lin.vo.OutDep;
import com.lin.vo.OutUser;
import com.lin.vo.UserDetailsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  组织部门角色API
 *  @author  lwz
 *  @date 2018.12.3
 */

@Api(description = "新通讯录-组织部门角色API")
@Controller
@RequestMapping("/organ")
public class OrganizationController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private OrganizationService organizationServiceDslImpl;
    @Autowired
    private AddressInfLogServiceImpl logServiceDsl;
    @Autowired
    private UserService userService;


    @GetMapping("getDepList")
    @ApiOperation(value="获取部门集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginID", value = "当前登入ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "provinceID", value = "省份ID", dataType = "String")
    })
    @ResponseBody
    public Result getDepList(HttpServletRequest req, String loginID, String provinceID) throws JsonProcessingException {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"获取部门集合");
        if(loginID == null || loginID.trim().equals("")) {
            logger.info("loginID 不能为空");
            return ResultGenerator.genErrorResult("loginID 不能为空");
        }
        try {
            List<OutDep> depList = this.organizationServiceDslImpl.getDepList(provinceID);
            logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(depList));
            return ResultGenerator.genSuccessResult(depList);
        } catch (Exception e) {
            com.ideal.wheel.common.Result result = ResultGenerator.genErrorResult(e.toString());
            logServiceDsl.saveAddressInfLog(log,mapper.writeValueAsString(result));
            return result;
        }
    }

/**
     * 查询组织部门
     * http://localhost:8866/app-addresslist/communicationdsl/organizationlist?pid=0
     * @param req
     * @param organ  当前登录人
     * @return
     *//*

    @GetMapping("organizationlist")
    @ApiOperation(value="查询组织部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginID", value = "当前登入ID", required = true, dataType = "String"),
            @ApiImplicitParam(dataType = "OrganizationDsl")
    })
    @ResponseBody
    public com.lin.util.Result organizationlist(HttpServletRequest req, OrganizationDsl organ, String loginID) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"组织部门");
        com.lin.util.Result result = new com.lin.util.Result();
        if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
            List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            orgtreeMap.put("organizationList", list);
            result.setRespCode("1");
            result.setRespDesc("正常返回数据");
            result.setRespMsg(orgtreeMap);
            logServiceDsl.saveAddressInfLog(log,result);
        } catch (Exception e) {
            ////e.printStackTrace();
            log.setExpError(e.toString());
            result.setRespCode("2");
            result.setRespDesc("失败");
            result.setRespMsg("");
            logServiceDsl.saveAddressInfLog(log,result);
        }
        return result;
    }
*/

}
