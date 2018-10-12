package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.AddressBannedRepository;
import com.lin.repository.AddressColAuxiliaryRepository;
import com.lin.repository.AddressCollectionRepository;
import com.lin.repository.AddressGroupRepository;
import io.swagger.models.auth.In;
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
public class GroupService extends AbstractService<AddressGroup,String>{

	private static final String String = null;

	@Autowired
	public GroupService(AddressGroupRepository addressGroupRepository) {
		super(addressGroupRepository);
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
		////查询类型 1角色
		if("1".equals(queryType)) {
			if (!"".equals(roleList) && null != roleList) {
				JSONObject obj = JSONObject.fromObject(roleList);
				JSONArray objlist = obj.getJSONArray("roleList");
				for (int i = 0; i < objlist.size(); i++) {
					JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
					String roleID = jsonObj.getString("roleID");
					String[] roles = roleID.split(",");
					for (int j = 0; j < roles.length; j++) {
						inroles += ",'" + roles[j] + "'";
					}
				}
				if (inroles.length() > 0) {
					inroles = inroles.substring(1);
				}
			}
		}

        //部门列表
        String indepts = "";
		////查询类型 1角色 2部门
		if("1".equals(queryType) || "2".equals(queryType)) {
			if (!"".equals(deptList) && null != deptList) {
				JSONObject obj = JSONObject.fromObject(deptList);
				JSONArray objlist = obj.getJSONArray("deptList");
				for (int i = 0; i < objlist.size(); i++) {
					JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
					String deptID = jsonObj.getString("deptID");
					userCount = userCount + getUserCountByDeptID(deptID, inroles);

				}
			}
		}
        //人员列表
        String inusers = "";
		////查询类型 1已有查询
		if("4".equals(queryType)) {
			if(!"".equals(groupID) && null != groupID){

				QAddressGroupUser qAddressGroupUser = QAddressGroupUser.addressGroupUser;
				long oldCount = queryFactory.select(qAddressGroupUser.groupId.count()).from(qAddressGroupUser).where(qAddressGroupUser.groupId.eq(groupID)).fetchCount();
				userCount = Integer.parseInt(oldCount+"");

				if(!"".equals(userList) && null != userList) {
					JSONObject obj = JSONObject.fromObject(userList);
					JSONArray objlist = obj.getJSONArray("userList");
					List<String> users = new ArrayList<String>();
					for (int i = 0; i < objlist.size(); i++) {
						JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
						String userID = jsonObj.getString("userID");
						users.add(userID);
					}
					long newCount = queryFactory.select(qAddressGroupUser.groupId.count()).from(qAddressGroupUser)
							.where(qAddressGroupUser.groupId.eq(groupID).and(qAddressGroupUser.groupUser.in(users))).fetchCount();
					userCount = userCount + users.size()-Integer.parseInt(newCount+"");;
				}
			}else{
				result.setRespCode("2");
				result.setRespDesc("groupID 不能为空");
				return ;
			}
        }

		JSONObject jsonObject   = new JSONObject();
        jsonObject.put("userCount",userCount);
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
		//角色列表ID
		String inroles = "";
		////查询类型 1角色
		if("1".equals(queryType)) {
			if (!"".equals(roleList) && null != roleList) {
				JSONObject obj = JSONObject.fromObject(roleList);
				JSONArray objlist = obj.getJSONArray("roleList");
				for (int i = 0; i < objlist.size(); i++) {
					JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
					String roleID = jsonObj.getString("roleID");
					String[] roles = roleID.split(",");
					for (int j = 0; j < roles.length; j++) {
						inroles += ",'" + roles[j] + "'";
					}
				}
				if (inroles.length() > 0) {
					inroles = inroles.substring(1);
				}
			}
		}

		//部门列表
		String indepts = "";
		////查询类型 1角色 2部门
		if("1".equals(queryType) || "2".equals(queryType)) {
			if (!"".equals(deptList) && null != deptList) {
				JSONObject obj = JSONObject.fromObject(deptList);
				JSONArray objlist = obj.getJSONArray("deptList");
				for (int i = 0; i < objlist.size(); i++) {
					JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
					String deptID = jsonObj.getString("deptID");


				}
			}
		}
	}


    /**
     * 根据部门ID获取最下级部门当前没有黑名单的人数
     * @param deptID
     * @return
     */
    private int getUserCountByDeptID(String deptID ,String roles){
        int i = 0 ;
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) cou" +
				"  from appuser.address_user u" +
				" where not exists" +
				" (select 1" +
				"          from appuser.address_blacklist tt" +
				"         where tt.user_id = u.user_id)" +
				"   and u.dep_id in" +
				"       (select t.organ_id" +
				"          from appuser.address_organization t" +
				"         where t.flag = '1'" +
				"           and not exists (select 1" +
				"                  from appuser.address_blackorglist tt" +
				"                 where t.organ_id = tt.org_id)" +
				"         start with t.organ_id = '"+ deptID +"'" +
				"        connect by prior t.organ_id = t.pid)");
		if (!"".equals(roles) && null != roles){
			sql.append(" and u.pos_id in ( " + roles + " )");
		}
		List list = entityManager.createNativeQuery(sql.toString()).getResultList();
	    return Integer.parseInt(list.get(0).toString());
    }


	@Override
	public long deleteByIds(String... ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AddressGroup> findByIds(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
