package com.lin.service;

import com.lin.util.NetUtil;
import com.lin.vo.JoinUsers;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极光推送一键建群辅助类
 * @author lwz 2018.10.12
 */
@Service
public class SendGroupService {


    @Value("${application.pic_HttpIP}")
    private String picHttpIp;
    @Value("${application.enterprise.createGroup}")
    private String enterpriseCreateGroup;
    @Value("${application.enterprise.inviteFriend}")
    private String enterpriseInviteFriend;
    @Value("${application.enterprise.removeMembers}")
    private String enterpriseRemoveMembers;
    @Value("${application.enterprise.exitGroup}")
    private String enterpriseExitGroup;
    @Value("${application.enterprise.dissolution}")
    private String enterpriseDissolution;
    @Value("${application.enterprise.modify}")
    private String enterpriseModify;
    @Value("${application.enterprise.queryGroup}")
    private String enterpriseQueryGroup;
    @Value("${application.enterprise.updateGroupInfo}")
    private String enterpriseUpdateGroupInfo;
    @Value("${application.enterprise.save2OutGroup}")
    private String enterpriseSave2OutGroup;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *      创建群组
     *      *      {
     *      *             "joinUsers": [
     *      *             {
     *      *                 "avatar": "http://42.99.16.176/mpi/plist_dir/plist_dir/pic/14/193492.png",
     *      *                     "nickName": "193492",
     *      *                     "customerId": "193492"
     *      *             },
     *      *             {
     *      *                 "avatar": "http://42.99.16.176/mpi/plist_dir/plist_dir/pic/1/117042.png",
     *      *                     "nickName": "冯朝晖",
     *      *                     "customerId": "117042"
     *      *             },
     *      *             {
     *      *                 "avatar": "http://42.99.16.176/232",
     *      *                     "nickName": "陈莹莹",
     *      *                     "customerId": "129510"
     *      *             }
     *      *     ],
     *      *             "crator": "193492",
     *      *                 "groupName": "测试群组",
     *      *                 "id": 232,
     *      *                 "avatar": "http://42.99.16.145:19491/im/211cf597-1e7f-4335-8ccd-1c56bee9c22d.png"
     *      *         }
     *      customerId staffId
     *      nickName 用户昵称
     *      avatar 用户头像
     * @param crator 创建人staffId
     * @param groupName 群组名称
     * @param id    群组id
     * @param avatar 群组头像
     * @param joinUsers 群成员列表
     *
     */
    public String createGroup(String crator, String groupName, String id, String avatar, List<JoinUsers> joinUsers){
        JSONObject obj = new JSONObject();
        obj.put("crator",crator);
        obj.put("groupName",groupName);
        obj.put("id","");
        obj.put("oneKeyFlag","1");
        obj.put("avatar",avatar);
        List<Map> listMap = new ArrayList<Map>();
        for(JoinUsers joinuser :joinUsers){
            Map map = new HashMap();
            map.put("avatar",picHttpIp + joinuser.getAvatar());
            map.put("nickName",joinuser.getNickName());
            map.put("customerId",joinuser.getCustomerId());
            listMap.add(map);
        }
        obj.put("joinUsers",listMap);

        logger.info("createGroup::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseCreateGroup,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *      添加成员
     *  {
     *     "sender": "888",
     *     "members": [
     *         {
     *             "groupId": "1",
     *             "nickName": "张三",
     *             "customerId": "12",
     *             "avatar": "http://42.99.16.176/mpi/plist_dir/plist_dir/pic/1/117042.png"
     *         }
     *     ]
     * }
     *      groupId 群组id
     *      customerId staffId
     *      nickName 用户昵称
     *      avatar 用户头像
     * @param sender 操作人staffId
     * @param members 邀请成员列表
     *
     */
    public Boolean inviteFriend(String sender ,List<JoinUsers> members){
        Boolean bool = false;
        JSONObject obj = new JSONObject();
        obj.put("sender",sender);
        obj.put("oneKeyFlag","1");
        List<Map> listMap = new ArrayList<Map>();
        for(JoinUsers joinuser :members){
            Map map = new HashMap();
            map.put("avatar",picHttpIp + joinuser.getAvatar());
            map.put("nickName",joinuser.getNickName());
            map.put("customerId",joinuser.getCustomerId());
            map.put("groupId",joinuser.getGroupId());
            listMap.add(map);
        }
        obj.put("joinUsers",listMap);
        logger.info("inviteFriend::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseInviteFriend,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0".equals(respCode)){
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }

    /**
     *      删除成员
     *  {
     *     "masterId": "888",
     *     "groupId": "628",
     *     "customerIds": "112"
     * }
     * @param masterId 操作人staffId
     * @param groupId 群ID
     * @param customerIds 删除成员staffID
     *
     */
    public Boolean removeMembers(String masterId,String groupId, List<JoinUsers> customerIds){
        Boolean bool = false;
        JSONObject obj = new JSONObject();
        obj.put("masterId",masterId);
        obj.put("groupId",groupId);
        obj.put("oneKeyFlag","1");
        List<Map> listMap = new ArrayList<Map>();
        String ids = "";
        for(JoinUsers joinuser :customerIds){
            ids += "_" + joinuser.getCustomerId();
        }
        if(!"".equals(ids)){
            ids.substring(1);
        }
        obj.put("customerIds",ids);
        logger.info("removeMembers::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseRemoveMembers,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0".equals(respCode)){
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }

    /**
     *      退出群组
     *  {
     *     "groupId": "628",
     *     "customerId": "112"
     * }
     * @param groupId 群ID
     * @param customerId 退出用户staffID
     *
     */
    public Boolean exitGroup(String groupId, String customerId){
        Boolean bool = false;
        JSONObject obj = new JSONObject();
        obj.put("groupId",groupId);
        obj.put("customerId",customerId);
        obj.put("oneKeyFlag","1");
        logger.info("exitGroup::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseExitGroup,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0".equals(respCode)){
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }

    /**
     *      解散群组
     *  {
     *     "groupId": "1",
     *     "masterId": "2"
     * }
     * @param groupId 群ID
     * @param masterId 群主staffID
     *
     */
    public Boolean dissolution(String groupId, String masterId){
        Boolean bool = false;
        JSONObject obj = new JSONObject();
        obj.put("groupId",groupId);
        obj.put("masterId",masterId);
        obj.put("oneKeyFlag","1");
        logger.info("dissolution::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseDissolution,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0".equals(respCode)){
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }

    /**
     *      设置分组状态
     *  {
     *     "crator":"99300",
     * 	   "groupId": "1569862",
     * 	   "status":"0"
     * }
     * @param groupId 群ID
     * @param crator 分组staffID
     *
     */
    public Boolean save2OutGroup(String groupId, String crator){
        Boolean bool = false;
        JSONObject obj = new JSONObject();
        obj.put("groupId",groupId);
        obj.put("crator",crator);
        obj.put("status","0");
        obj.put("oneKeyFlag","1");
        logger.info("save2OutGroup::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseSave2OutGroup,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0".equals(respCode)){
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }

    /**
     *      修改群名
     *  {
     *     "id": "1",
     * "groupName": "修改后的群名",
     * "customerId": "2"
     * }
     * @param id 群ID
     * @param groupName 群name
     * @param customerId 修改人ID
     *
     */
    public Boolean modify(String id,String groupName, String customerId){
        Boolean bool = false;
        JSONObject obj = new JSONObject();
        obj.put("id",id);
        obj.put("groupName",groupName);
        obj.put("customerId",customerId);
        obj.put("oneKeyFlag","1");
        logger.info("modify::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseModify,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0".equals(respCode)){
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }

    /**
     *      查询群组列表
     *  {
     *     "customerId": "104506"
     * }
     * @param customerId 修改人ID
     *
     */
    public Map queryGroup( String customerId){
        Map map = new HashMap();
        map.put("bool",false);
        JSONObject obj = new JSONObject();
        obj.put("queryGroup",customerId);
        obj.put("oneKeyFlag","1");
        logger.info("modify::"+obj.toString());
        List<JoinUsers> joinUsers = new ArrayList<JoinUsers>();
        try {
            String result = NetUtil.send(enterpriseQueryGroup,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0000".equals(respCode)){
                map.put("bool",true);
                JSONArray objArray = objR.getJSONArray("groupList");
                if (0 < objArray.size()) {
                    for (int i = 0; i < objArray.size(); i++) {
                        JSONObject objJoin = JSONObject.fromObject(objArray.get(i));
                        JoinUsers joinUser = new JoinUsers();
                        joinUser.setAvatar(objJoin.getString("avatar"));
                        joinUser.setGroupId(objJoin.getString("groupId"));
                        joinUser.setGroupName(objJoin.getString("groupName"));
                        joinUsers.add(joinUser);
                    }
                }
            }
            map.put("groups",joinUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }

    /**
     *    群头像更新
     *      *  {
     *      *     "groupId": "628",
     *      *     "avatar": "http://42.99.16.145:19491/1/mphotos/11/136684.png"
     *      * }
     * @param groupId
     * @param avatar
     * @return
     */
    public Boolean updateGroupInfo(String groupId,String avatar){
        Boolean bool = false;
        JSONObject obj = new JSONObject();
        obj.put("groupId",groupId);
        obj.put("avatar",picHttpIp + avatar);
        obj.put("oneKeyFlag","1");
        logger.info("updateGroupInfo::"+obj.toString());
        try {
            String result = NetUtil.send(enterpriseUpdateGroupInfo,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
            JSONObject objR = JSONObject.fromObject(result);
            String respCode = objR.getString("respCode");
            if ("0".equals(respCode)){
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bool;
        }
        return bool;
    }
}
