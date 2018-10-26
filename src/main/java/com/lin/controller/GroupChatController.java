package com.lin.controller;

import com.lin.domain.AddressInfLog;
import com.lin.service.AddressInfLogServiceImpl;
import com.lin.service.GroupChatService;
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
@RestController
@RequestMapping("/groupChat")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;
    @Autowired
    private AddressInfLogServiceImpl logServiceDsl;

    /**
     * 创建讨论组
     * @return
     */
    @PostMapping("createGroup")
    @ApiOperation(value="创建讨论组")
    public Result createGroup(HttpServletRequest req,@RequestBody InCreateGroup inCreateGroup) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"极光创建讨论组",
                  inCreateGroup.toString() +"&loginID="+inCreateGroup.getCrator());
        Result result = new Result();
        result.setRespMsg("");
        if (null == inCreateGroup.getId() || "".equals(inCreateGroup.getId())) {
            result.setRespCode("2");
            result.setRespDesc("群组id 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inCreateGroup.getGroupName() || "".equals(inCreateGroup.getGroupName())) {
            result.setRespCode("2");
            result.setRespDesc("群组名称 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inCreateGroup.getCrator() || "".equals(inCreateGroup.getCrator())) {
            result.setRespCode("2");
            result.setRespDesc("创建人 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
            groupChatService.createGroup(result,inCreateGroup);
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
    /**
     * 添加成员
     * @return
     */
    @PostMapping("inviteFriend")
    @ApiOperation(value="添加成员")
    public Result inviteFriend(HttpServletRequest req,@RequestBody InInviteFriend inInviteFriend) {
        AddressInfLog log =  logServiceDsl.getInfLog(req,"极光添加成员",
                inInviteFriend.toString() +"&loginID="+inInviteFriend.getSender());
        Result result = new Result();
        result.setRespMsg("");
        if (null == inInviteFriend.getSender() || "".equals(inInviteFriend.getSender())) {
            result.setRespCode("2");
            result.setRespDesc("操作人 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
            groupChatService.inviteFriend(result,inInviteFriend);
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
        AddressInfLog log =  logServiceDsl.getInfLog(req,"极光删除成员",
                inRemoveMembers.toString() +"&loginID="+inRemoveMembers.getMasterId());
        Result result = new Result();
        result.setRespMsg("");
        if (null == inRemoveMembers.getMasterId() || "".equals(inRemoveMembers.getMasterId())) {
            result.setRespCode("2");
            result.setRespDesc("操作人 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inRemoveMembers.getGroupId() || "".equals(inRemoveMembers.getGroupId())) {
            result.setRespCode("2");
            result.setRespDesc("群ID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
            groupChatService.removeMembers(result,inRemoveMembers);
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
        AddressInfLog log =  logServiceDsl.getInfLog(req,"极光群头像更新",
                inUpdateGroupInfo.toString() +"&loginID="+inUpdateGroupInfo.getMasterId());
        Result result = new Result();
        result.setRespMsg("");
        if (null == inUpdateGroupInfo.getMasterId() || "".equals(inUpdateGroupInfo.getMasterId())) {
            result.setRespCode("2");
            result.setRespDesc("操作人 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inUpdateGroupInfo.getAvatar() || "".equals(inUpdateGroupInfo.getAvatar())) {
            result.setRespCode("2");
            result.setRespDesc("群头像 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inUpdateGroupInfo.getGroupId() || "".equals(inUpdateGroupInfo.getGroupId())) {
            result.setRespCode("2");
            result.setRespDesc("群ID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
           groupChatService.updateGroupInfo(result,inUpdateGroupInfo);
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
        AddressInfLog log =  logServiceDsl.getInfLog(req,"极光退出群组",
                inExitGroup.toString() +"&loginID="+inExitGroup.getCustomerId());
        Result result = new Result();
        result.setRespMsg("");
        if (null == inExitGroup.getCustomerId() || "".equals(inExitGroup.getCustomerId())) {
            result.setRespCode("2");
            result.setRespDesc("退出用户 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inExitGroup.getGroupId() || "".equals(inExitGroup.getGroupId())) {
            result.setRespCode("2");
            result.setRespDesc("群ID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
           groupChatService.exitGroup(result,inExitGroup);
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
        AddressInfLog log =  logServiceDsl.getInfLog(req,"极光解散群组",
                inDissolution.toString() +"&loginID="+inDissolution.getMasterId());
        Result result = new Result();
        result.setRespMsg("");
        if (null == inDissolution.getMasterId() || "".equals(inDissolution.getMasterId())) {
            result.setRespCode("2");
            result.setRespDesc("操作人 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inDissolution.getGroupId() || "".equals(inDissolution.getGroupId())) {
            result.setRespCode("2");
            result.setRespDesc("群ID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
            groupChatService.dissolution(result,inDissolution);
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
        AddressInfLog log =  logServiceDsl.getInfLog(req,"极光修改群名",
                inModify.toString() +"&loginID="+inModify.getCustomerId());
        Result result = new Result();
        result.setRespMsg("");
        if (null == inModify.getCustomerId() || "".equals(inModify.getCustomerId())) {
            result.setRespCode("2");
            result.setRespDesc("操作人 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inModify.getGroupName() || "".equals(inModify.getGroupName())) {
            result.setRespCode("2");
            result.setRespDesc("群名 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        if (null == inModify.getId() || "".equals(inModify.getId())) {
            result.setRespCode("2");
            result.setRespDesc("群ID 不能为空");
            logServiceDsl.saveAddressInfLog(log,result);
            return result;
        }
        try {
            groupChatService.modify(result,inModify);
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
