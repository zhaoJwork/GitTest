package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.AddressBannedRepository;
import com.lin.repository.AddressColAuxiliaryRepository;
import com.lin.repository.AddressCollectionRepository;
import com.lin.repository.GroupRepository;
import net.sf.json.JSONArray;
import com.lin.util.Result;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.*;


/**
 *
 * 通讯录
 * @author lwz
 * @date 2018.10.10
 *
 */
@Service("newGroupService")
public class GroupService extends AbstractService<GroupBo,String>{

	private static final String String = null;

	@Autowired
	public GroupService(GroupRepository groupRepository) {
		super(groupRepository);
	}

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


	@Resource
	private AddressBannedRepository addressBannedRepository;

	@Resource
	private AddressCollectionRepository addressCollectionRepository;

	@Resource
	private AddressColAuxiliaryRepository addressColAuxiliaryRepository;

	@Autowired
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}


	/**
	 * 角色信息查询
	 * @param result
	 * @param loginID
	 * @param queryType
	 */
	public void selectRoleDept(Result result, String loginID, String queryType){
		QPositionDsl qPositionDsl = QPositionDsl.positionDsl;
		String db = "select * from (select to_char(wm_concat(p.pos_id)) pos_id,p.pos_name,min(p.role_num) role_num from appuser.address_position p group by p.pos_name) t order by t.role_num";
		List positionList = entityManager.createNativeQuery(db).getResultList();
        JSONObject jsonObject   = new JSONObject();
        List<Map> listMap = new ArrayList<Map>();
		for (int i = 0 ;i < positionList.size() ; i++){
		    Map map = new HashMap();
			Object[] obj = (Object[])positionList.get(i);
            map.put("dateKey",obj[0]);
            map.put("dateValue",obj[1]);
            listMap.add(map);
		}
        jsonObject.put("dateList",listMap);
		result.setRespCode("1");
		result.setRespDesc("正常返回数据");
		result.setRespMsg(jsonObject);
	}

	/**
	 * 统计当前人数查询
	 * @param result
	 * @param loginID
	 * @param queryType
	 * @param roleList
	 * @param deptList
	 * @param userList
	 * @param groupID
	 */
	public void selectGroupCount(Result result, String loginID, String queryType, String roleList, String deptList, String userList, String groupID){
	    //当前返回值人员数量
	    int userCount = 0;
	    //角色列表ID
	    String inroles = "";
        if(!"".equals(roleList) && null != roleList){
            JSONObject obj = JSONObject.fromObject(roleList);
            JSONArray objlist = obj.getJSONArray("roleList");
            for (int i = 0; i < objlist.size(); i++) {
                JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
                String roleID = jsonObj.getString("roleID");
                String[] roles= roleID.split(",");
                for(int j = 0; j < roles.length; i ++){
                    inroles += "'"+ roles[j] + "',";
                }
            }
        }
        //部门列表
        String indepts = "";
        if(!"".equals(roleList) && null != roleList){
            JSONObject obj = JSONObject.fromObject(deptList);
            JSONArray objlist = obj.getJSONArray("deptList");
            for (int i = 0; i < objlist.size(); i++) {
                JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
                String deptID = jsonObj.getString("deptID");

                /*
                *   select * from appuser.address_organization t
                    where t.flag = '1'
                    and not exists (
                        select 1 from appuser.address_blackorglist tt where t.organ_id = tt.org_id
                    )
                    start with t.organ_id = '1844641'
                    connect by prior t.organ_id =  t.pid
                * */

                String[] depts= deptID.split(",");
                for(int j = 0; j < depts.length; i ++){
                    indepts += "'"+ depts[j] + "',";
                }
            }
        }
        //部门列表
        String inusers = "";
        if(!"".equals(roleList) && null != roleList){
            JSONObject obj = JSONObject.fromObject(deptList);
            JSONArray objlist = obj.getJSONArray("deptList");
            for (int i = 0; i < objlist.size(); i++) {
                JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
                String userID = jsonObj.getString("userID");
                String[] users= userID.split(",");
                for(int j = 0; j < users.length; i ++){
                    inusers += "'"+ users[j] + "',";
                }
            }
        }


        ////查询类型 1角色
	    if("1".equals(queryType)){

        ////查询类型 1角色2部门3自定义4已有分组
        }else if("2".equals(queryType)){

	    ////查询类型 1角色2部门3自定义4已有分组
        }else if("3".equals(queryType)){

        ////查询类型 1角色2部门3自定义4已有分组
        }else if("4".equals(queryType)){

        }




            QPositionDsl qPositionDsl = QPositionDsl.positionDsl;
        String db = "select * from (select to_char(wm_concat(p.pos_id)) pos_id,p.pos_name,min(p.role_num) role_num from appuser.address_position p group by p.pos_name) t order by t.role_num";
        List positionList = entityManager.createNativeQuery(db).getResultList();
        JSONObject jsonObject   = new JSONObject();
        List<Map> listMap = new ArrayList<Map>();
        for (int i = 0 ;i < positionList.size() ; i++){
            Map map = new HashMap();
            Object[] obj = (Object[])positionList.get(i);
            map.put("dateKey",obj[0]);
            map.put("dateValue",obj[1]);
            listMap.add(map);
        }
        jsonObject.put("dateList",listMap);
        result.setRespCode("1");
        result.setRespDesc("正常返回数据");
        result.setRespMsg(jsonObject);
	}

	/**
	 * 新分组保存功能
	 * @param result
	 * @param loginID
	 * @param queryType
	 * @param roleList
	 * @param deptList
	 * @param userList
	 * @param groupID
	 * @param groupName
	 * @param groupDesc
	 */
	public void saveGroupCount(Result result, String loginID, String queryType, String roleList, String deptList, String userList, String groupID,String groupName,String groupDesc){

	}


    /**
     * 根据部门ID获取最下级部门当前没有黑名单的人数
     * @param deptID
     * @return
     */
    private long getUserCountByDeptID(String deptID){
        long i = 0 ;
        QUser qUser = QUser.user;
        i = queryFactory.select(qUser.count()).from(qUser)
                .where()
                .fetchCount();
	    return i;
    }


	@Override
	public long deleteByIds(String... ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GroupBo> findByIds(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
