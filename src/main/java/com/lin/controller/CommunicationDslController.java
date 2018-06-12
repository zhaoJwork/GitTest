package com.lin.controller;

import com.lin.domain.AddressInfLogDsl;
import com.lin.domain.OrganizationDsl;
import com.lin.service.AddressInfLogServiceDslImpl;
import com.lin.service.OrganizationServiceDslImpl;
import com.lin.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  dsl 新方法
 *  @author  lwz 2018.6.11
 */

@Api(description = "通讯录API")
@Controller
@RequestMapping("/communicationdsl")
public class CommunicationDslController {

    @Autowired
    private OrganizationServiceDslImpl organizationServiceDslImpl;
    @Autowired
    private AddressInfLogServiceDslImpl logServiceDsl;
    /**
     * 查询组织部门
     * http://localhost:8866/app-addresslist/communicationdsl/organizationlist?pid=0
     * @param req
     * @param organ  当前登录人
     * @return
     */
    @ApiOperation(value="查询组织部门",tags = {"1s"})
    @GetMapping("organizationlist")
    @ResponseBody
    public Result organizationlist(HttpServletRequest req, OrganizationDsl organ,String loginID) {
        AddressInfLogDsl log =  logServiceDsl.getInfLog(req,"组织部门");
        Result result = new Result();
        try {
            List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            orgtreeMap.put("organizationList", list);
            result.setRespCode("1");
            result.setRespDesc("正常返回数据");
            result.setRespMsg(orgtreeMap);
            logServiceDsl.saveAddressInfLog(log,result);
        } catch (Exception e) {
            e.printStackTrace();
            ////log.setExpError(e.toString());
            result.setRespCode("2");
            result.setRespDesc("失败");
            result.setRespMsg("");
            logServiceDsl.saveAddressInfLog(log,result);
        }
        return result;
    }

}
