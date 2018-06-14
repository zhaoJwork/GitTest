package com.lin.controller;

import com.lin.domain.*;
import com.lin.service.AddressInfLogServiceDslImpl;
import com.lin.service.OrganizationServiceDslImpl;
import com.lin.service.UserService;
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
    @Autowired
    private UserService userService;
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
            ////e.printStackTrace();
            log.setExpError(e.toString());
            result.setRespCode("2");
            result.setRespDesc("失败");
            result.setRespMsg("");
            logServiceDsl.saveAddressInfLog(log,result);
        }
        return result;
    }

    /**
     *
     * @return Result
     * @author hzh
     * @date 2017年8月18日
     * @describe 描述 获取个人详情
     *           http://localhost:8866/app-addresslist/communicationdsl/userdetails?loginID=101948&userID=101948
     */
    @RequestMapping("userdetails")
    @ResponseBody
    public Result userdetails(HttpServletRequest req, String loginID, String userID) {
        AddressInfLogDsl log =  logServiceDsl.getInfLog(req,"个人详情");
        Result result = new Result();
        if (null == loginID  || "".equals(loginID.trim())) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == userID || "".equals(userID.trim())) {
            result.setRespCode("2");
            result.setRespDesc("人员ID不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }else{
            try {
            UserDetailsDsl uu = userService.selectUserDetails(loginID,userID);
            if(null == uu){
                result.setRespCode("2");
                result.setRespDesc("该用户不在通讯录中,暂不提供人员信息");
                logServiceDsl.saveAddressInfLog(log,result);
                return result;
            }else{
                  ////  UserDetails ud = userService.SelectUserDetailsById(userID);
                    result.setRespCode("1");
                    result.setRespDesc("正常返回数据");
                    result.setRespMsg(uu);

            }
            } catch (Exception e) {
                log.setExpError(e.toString());
                result.setRespCode("2");
                result.setRespDesc("失败");
                result.setRespMsg("");
            }
        }
        logServiceDsl.saveAddressInfLog(log,result);
        return result;
    }

}
