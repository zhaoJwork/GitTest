package com.lin.service;

import com.lin.util.NetUtil;
import com.lin.vo.JoinUsers;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极光推送辅助类
 * @author lwz 2018.10.12
 */
@Service
public class SendGroupService {

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
    public void createGroup(String crator, String groupName, String id, String avatar, List<JoinUsers> joinUsers){
        JSONObject obj = new JSONObject();
        obj.put("crator",crator);
        obj.put("groupName",groupName);
        obj.put("id",id);
        obj.put("avatar",avatar);
        List<Map> listMap = new ArrayList<Map>();
        for(JoinUsers joinuser :joinUsers){
            Map map = new HashMap();
            map.put("avatar",joinuser.getAvatar());
            map.put("nickName",joinuser.getNickName());
            map.put("customerId",joinuser.getCustomerId());
            listMap.add(map);
        }
        obj.put("joinUsers",listMap);
        try {
            String result = NetUtil.send(enterpriseCreateGroup,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
        } catch (Exception e) {
            e.printStackTrace();
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
    public void inviteFriend(String sender, List<JoinUsers> members){
        JSONObject obj = new JSONObject();
        obj.put("sender",sender);
        List<Map> listMap = new ArrayList<Map>();
        for(JoinUsers joinuser :members){
            Map map = new HashMap();
            map.put("avatar",joinuser.getAvatar());
            map.put("nickName",joinuser.getNickName());
            map.put("customerId",joinuser.getCustomerId());
            map.put("groupId",joinuser.getGroupId());
            listMap.add(map);
        }
        obj.put("joinUsers",listMap);
        try {
            String result = NetUtil.send(enterpriseInviteFriend,
                    "POST",
                    obj.toString(),
                    "application/json;charset=utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
