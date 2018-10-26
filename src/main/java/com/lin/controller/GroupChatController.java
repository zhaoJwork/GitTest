package com.lin.controller;

import com.lin.domain.AddressInfLog;
import com.lin.service.AddressInfLogServiceImpl;
import com.lin.service.OrganizationService;
import com.lin.service.UserService;
import com.lin.util.Result;
import com.lin.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz 2018.10.26
 */

@Api(description = "即时通讯调用通讯录群聊分组")
@Controller
@RequestMapping("/groupChat")
public class GroupChatController {

    @Autowired
    private OrganizationService organizationServiceDslImpl;
    @Autowired
    private AddressInfLogServiceImpl logServiceDsl;
    @Autowired
    private UserService userService;
    /**
     * 创建讨论组
     * @return
     */
    @PostMapping("createGroup")
    @ApiOperation(value="创建讨论组")
    public Result createGroup(HttpServletRequest req,@RequestBody InCreateGroup inCreateGroup) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"创建讨论组");
        Result result = new Result();
       /* if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }*/
        try {
           // List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
         //   orgtreeMap.put("organizationList", list);
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
     * 添加成员
     * @return
     */
    @PostMapping("inviteFriend")
    @ApiOperation(value="添加成员")
    public Result inviteFriend(HttpServletRequest req,@RequestBody InInviteFriend inInviteFriend) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"添加成员");
        Result result = new Result();
       /* if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }*/
        try {
            // List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            //   orgtreeMap.put("organizationList", list);
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
     * 删除成员
     * @return
     */
    @PostMapping("removeMembers")
    @ApiOperation(value="删除成员")
    public Result removeMembers(HttpServletRequest req,@RequestBody InRemoveMembers inRemoveMembers) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"删除成员");
        Result result = new Result();
       /* if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }*/
        try {
            // List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            //   orgtreeMap.put("organizationList", list);
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
     * 群头像更新
     * @return
     */
    @PostMapping("updateGroupInfo")
    @ApiOperation(value="群头像更新")
    public Result updateGroupInfo(HttpServletRequest req,@RequestBody InUpdateGroupInfo inUpdateGroupInfo) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"群头像更新");
        Result result = new Result();
       /* if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }*/
        try {
            // List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            //   orgtreeMap.put("organizationList", list);
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
     * 退出群组
     * @return
     */
    @PostMapping("exitGroup")
    @ApiOperation(value="退出群组")
    public Result exitGroup(HttpServletRequest req,@RequestBody InExitGroup inExitGroup) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"退出群组");
        Result result = new Result();
       /* if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }*/
        try {
            // List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            //   orgtreeMap.put("organizationList", list);
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
     * 解散群组
     * @return
     */
    @PostMapping("dissolution")
    @ApiOperation(value="解散群组")
    public Result dissolution(HttpServletRequest req,@RequestBody InDissolution inDissolution) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"解散群组");
        Result result = new Result();
       /* if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }*/
        try {
            // List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            //   orgtreeMap.put("organizationList", list);
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
     * 修改群名
     * @return
     */
    @PostMapping("modify")
    @ApiOperation(value="修改群名")
    public Result modify(HttpServletRequest req,@RequestBody InModify inModify) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"修改群名");
        Result result = new Result();
       /* if (null == loginID || "".equals(loginID)) {
            result.setRespCode("2");
            result.setRespDesc("loginID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }*/
        try {
            // List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            //   orgtreeMap.put("organizationList", list);
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
}
