package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.*;
import com.lin.util.ImageUtil;
import com.lin.util.NetUtil;
import com.lin.vo.JoinUsers;
import com.lin.vo.UserDetailsVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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


	@Value("${application.pic_HttpIP}")
	private String picHttpIp;
	@Value("${application.pic_group_img}")
	private String picGroupImg;
	@Value("${application.pic_group_temp_img}")
	private String picGroupTempImg;
	@Value("${application.pic_group_db_img_root}")
	private String picGroupDbImgRoot;
	@Value("${application.pic_group_db_img}")
	private String picGroupDbImg;

	@Resource
	private AddressGroupRepository addressGroupRepository;
	@Resource
	private AddressGroupUserRepository addressGroupUserRepository;
	@Autowired
	private SendGroupService sendGroupService;
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
				////保存主表数据
				AddressGroup addressGroup = new AddressGroup();
				addressGroup.setGroupName(groupName);
				addressGroup.setGroupDesc(groupDesc);
				groupID = getSeq()+"";
				addressGroup.setRowId(groupID);
				addressGroup.setGroupId(groupID);
				addressGroup.setCreateUser(loginID);
				addressGroup.setCreateDate(new Date());
				addressGroup.setUpdateDate(new Date());
				addressGroup.setGroupImg("/1/mphotos/10000001.png");
				addressGroupRepository.save(addressGroup);
				////保存子表数据
				JSONObject obj = JSONObject.fromObject(deptList);
				JSONArray objlist = obj.getJSONArray("deptList");
				for (int i = 0; i < objlist.size(); i++) {
					JSONObject jsonObj = JSONObject.fromObject(objlist.getString(i));
					String deptID = jsonObj.getString("deptID");
					List list = getUserByDeptID(deptID,inroles);
					List<AddressGroupUser> listgu = new ArrayList<AddressGroupUser>();
					for(int m = 0 ; m < list.size() ; m ++){
						AddressGroupUser addressGroupUser = new AddressGroupUser();
						addressGroupUser.setRowId(getSeq()+"");
						addressGroupUser.setGroupId(groupID);
						addressGroupUser.setGroupUser(list.get(m).toString());
						addressGroupUser.setCreateDate(new Date());
						listgu.add(addressGroupUser);
					}
					addressGroupUserRepository.saveAll(listgu);
				}
				////更新头像
				try {
					editGroupImg(groupID);
				} catch (Exception e) {
					e.printStackTrace();
				}

				////推送极光推送
				QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
				QAddressGroupUser qAddressGroupUser = QAddressGroupUser.addressGroupUser;
				QUser qUser = QUser.user;
				QUserNewAssist uass = QUserNewAssist.userNewAssist;
				AddressGroup group = queryFactory.select(qAddressGroup).from(qAddressGroup).where(qAddressGroup.groupId.eq(groupID)).fetchOne();
				List<JoinUsers> listJoinUsers = queryFactory.select(Projections.bean(JoinUsers.class,
						 							qAddressGroupUser.groupUser.as("customerId"),
						 							qUser.userName.as("nickName"),
						 							new CaseBuilder().when(uass.portrait_url.eq("").or(uass.portrait_url.isNull())).then(qUser.userPic).otherwise(uass.portrait_url).as("avatar")
						 ))
						 .from(qAddressGroupUser)
						 .leftJoin(qUser)
						 .on(qAddressGroupUser.groupUser.eq(qUser.userID))
						 .leftJoin(uass)
						 .on(qUser.userID.eq(uass.userid))
						 .where(qAddressGroupUser.groupId.eq(groupID)).fetch();
				sendGroupService.createGroup(group.getCreateUser(),group.getGroupName(),group.getGroupId(),picHttpIp + group.getGroupImg(),listJoinUsers);
			}
		}
	}




	/**
	 * 编辑图片
	 * @param groupID
	 */
	private void editGroupImg(String groupID) throws Exception{
		// 查询所属组的人

		QUser qUser = QUser.user;
		QUserNewAssist uass = QUserNewAssist.userNewAssist;
		QAddressGroupUser qAddressGroupUser = QAddressGroupUser.addressGroupUser;
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		List<User> userIdList = queryFactory.select(Projections.bean(User.class,
					qAddressGroupUser.groupUser.as("userID"),
					new CaseBuilder().when(uass.portrait_url.eq("").or(uass.portrait_url.isNull())).then(qUser.userPic).otherwise(uass.portrait_url).as("userPic")
				)
			).from(qAddressGroupUser)
			.leftJoin(qUser)
			.on(qAddressGroupUser.groupUser.eq(qUser.userID))
			.leftJoin(uass)
			.on(qUser.userID.eq(uass.userid))
			.where(qAddressGroupUser.groupId.eq(groupID)).fetch();

		////List<User> userIdList = groupDao.selectGroupUserBygroupID(groupID);
		String imgIp = picHttpIp;
		//// 临时文件路径
		String tem = picGroupTempImg;
		////文件所缺根路径
		String imgRoot = picGroupDbImgRoot;
		List<File> fileList = new ArrayList<File>();
		for (User u : userIdList) {
			String uPic = u.getUserPic();
			if (uPic == null || uPic.equals(imgIp + "/1/mphotos/10000001.png")) {
				// 10000001.png数据库中因为不能有空数据，所以写死的假数据
				continue;
			}
			String uPicTem = tem + u.getUserID() + ".png";
			try {
				uPic=uPic.replace(imgIp,imgRoot);
				File fromFile = new File(uPic);
				File toFile = new File(uPicTem);
				copyFile(fromFile,toFile);
				File f = new File(uPicTem);
				fileList.add(f);
				// 只要九个图片(方法只允许最多9张图片)
				if (fileList.size() == 9) {
					break;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fileList.size() > 0) {
			//文件名称不能单用groupId生成 如果前端设置本地缓存 则图片不会更新
			String union=System.currentTimeMillis()+"";
			// 存放群组图片地址服务器文件路径
			String groupImgAddress = picGroupImg + union+ groupID + ".png";
			// 9张图片生成1张图片
			ImageUtil.createImage(fileList, groupImgAddress, "");
			File f = new File(groupImgAddress);
			String saveUrl = picGroupDbImg + union+ groupID + ".png";
			// 更新appuser.address_group表
			if (f.exists()) {
				AddressGroup addressGroup = queryFactory.select(qAddressGroup).from(qAddressGroup).where(qAddressGroup.groupId.eq(groupID)).fetchOne();
				addressGroup.setUpdateDate(new Date());
				addressGroup.setGroupImg(saveUrl);
				super.update(addressGroup);
			}
		}
	}

	/**
	 * 复制文件
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public void copyFile(File fromFile,File toFile) throws IOException{
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n=0;
		while((n=ins.read(b))!=-1){
			out.write(b, 0, n);
		}

		ins.close();
		out.close();
	}
    /**
     * 根据部门ID获取最下级部门当前没有黑名单的人数
     * @param deptID
	 * @param roles
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

	/**
	 * 根据部门ID获取最下级部门当前没有黑名单的人员ID
	 * @param deptID
	 * @param roles
	 * @return
	 */
	private List getUserByDeptID(String deptID ,String roles){
		int i = 0 ;
		StringBuffer sql = new StringBuffer();
		sql.append("select u.user_id " +
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
		return entityManager.createNativeQuery(sql.toString()).getResultList();
	}

	private Integer getSeq(){
		List noTalk = entityManager.createNativeQuery("select appuser.seq_app_addresslist.nextval from dual")
				.getResultList();
		return Integer.parseInt(noTalk.get(0).toString());
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
