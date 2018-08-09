package com.lin.service.impl;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import com.lin.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lin.mapper.GroupMapper;
import com.lin.mapper.UtilMapper;
import com.lin.repository.AddressGroupRepository;
import com.lin.service.GroupServiceI;
import com.lin.util.ImageUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 *
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Service("groupService")
public class GroupServiceImpl implements GroupServiceI {

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

	@Autowired
	private GroupMapper groupDao;
	@Autowired
	private UtilMapper utilDao;
	
	// sql 修改为jpa+querydsl 
	
	private AddressGroupRepository addressGroupRepository;
	@Autowired
    private EntityManager entityManager;
	
	private JPAQueryFactory queryFactory;  
    
    @PostConstruct  
    public void init() {  
       queryFactory = new JPAQueryFactory(entityManager);  
    }
	

	@Override
	public List<Group> selectAllGroupByLoginID(String loginID,String groupname) {
		GroupBean gb = new GroupBean();
		gb.setCreateUser(loginID);
		gb.setGroupName(groupname);
		List<Group> list = groupDao.selectAllGroupByLoginID(gb);
		return list;
	}

	/**
	 * 编辑个人分组 loginID=22295& groupID=& groupName=以小组分组& groupDesc=分组详情&
	 * userIds=123_234_456& type=1增加人员 2 删除人员 3 删除组
	 * 
	 * @throws Exception
	 */
	@Override
	public void editGroup(String loginID, String groupID, String groupName, String groupDesc, String userIds,
			String type) throws Exception {
		// 验证是否为新建组
		if (null == groupID || "".equals(groupID)) {
			String seq = utilDao.getSeqAppAddresslist();
			groupID = groupDao.getNGroupID();
			GroupBean gb = new GroupBean();
			gb.setRowID(seq);
			gb.setGroupID(groupID);
			gb.setGroupName(groupName);
			gb.setGroupDesc(groupDesc);
			gb.setCreateUser(loginID);
			groupDao.saveGroup(gb);
			if (null != userIds && !"".equals(userIds)) {
				String[] userID = userIds.split("_");
				List<GroupUserBean> listGU = new ArrayList<GroupUserBean>();
				for (int i = 0; i < userID.length; i++) {
					GroupUserBean gu = new GroupUserBean();
					String uid = userID[i];
					String rowID = utilDao.getSeqAppGourpUser();
					gu.setRowID(rowID);
					gu.setGroupID(groupID);
					gu.setGroupUser(uid);
					listGU.add(gu);
				}
				groupDao.saveGroupUser(listGU);
				// 创建群组 成功 生成群组头像 zhangWeiJie
				editGroupImg(groupID);
				gb.setGroupName("/1/mphotos/10000001.png");
				groupDao.updateGroupImgInfo(gb);
			}
		} else {
			if (null != type && !"".equals(type)) {
				if (type.equals("1")) {// type=1增加人员
					if (null != userIds && !"".equals(userIds)) {
						String[] userID = userIds.split("_");
						//不重复增加组人员
						String groupUsers = "";
						groupUsers = groupDao.getGroupUserIDs(groupID);
						List<GroupUserBean> listGU = new ArrayList<GroupUserBean>();
						for (int i = 0; i < userID.length; i++) {
							GroupUserBean gu = new GroupUserBean();
							String uid = userID[i];
							if(null == groupUsers || "".equals(groupUsers)){
								String rowID = utilDao.getSeqAppGourpUser();
								gu.setRowID(rowID);
								gu.setGroupID(groupID);
								gu.setGroupUser(uid);
								listGU.add(gu);
							}else {
								if(groupUsers.indexOf(uid) < 0){
									String rowID = utilDao.getSeqAppGourpUser();
									gu.setRowID(rowID);
									gu.setGroupID(groupID);
									gu.setGroupUser(uid);
									listGU.add(gu);
								}
							}
						}
						 if(0 < listGU.size()) {
							 groupDao.saveGroupUser(listGU);
						 }
					}
				} else if (type.equals("2")) {// 2 删除人员
					if (null != userIds && !"".equals(userIds)) {
						String[] userID = userIds.split("_");
						GroupUserBean gu = new GroupUserBean();
						String groupUser = "";
						for (int i = 0; i < userID.length; i++) {
							String uid = userID[i];
							groupUser += "'" + uid + "',";
						}
						groupUser = groupUser.substring(0, groupUser.lastIndexOf(","));
						gu.setGroupID(groupID);
						gu.setGroupUser(groupUser);
						groupDao.deleteGroupUser(gu);
					}
				} else if (type.equals("3")) {// 3 删除组
					groupDao.deleteGroup(groupID);
					GroupUserBean gu = new GroupUserBean();
					gu.setGroupID(groupID);
					groupDao.deleteGroupUser(gu);
				}
				// 编辑群组人员，重新生成群组头像 zhangWeiJie
				editGroupImg(groupID);
			}
			// groupName=以小组分组&
			if (null != groupName && !"".equals(groupName)) {
				GroupBean gb = new GroupBean();
				gb.setGroupID(groupID);
				gb.setGroupName(groupName);
				groupDao.updateGroup(gb);
			}
			// groupDesc=分组详情
			if (null != groupDesc && !"".equals(groupDesc)) {
				GroupBean gb = new GroupBean();
				gb.setGroupID(groupID);
				gb.setGroupDesc(groupDesc);
				groupDao.updateGroup(gb);
			}
		}
	}

	private void editGroupImg(String groupID) throws Exception {
		// 查询所属组的人
		List<User> userIdList = groupDao.selectGroupUserBygroupID(groupID);
		String imgIp = picHttpIp;// "http://42.99.16.145:19491";//
											// 头像缺少IP地址
		String tem = picGroupTempImg;// 临时文件路径
		String imgRoot = picGroupDbImgRoot;//文件所缺根路径
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
				GroupBean gb = new GroupBean();
				gb.setGroupID(groupID);
				gb.setGroupName(saveUrl);
				groupDao.updateGroupImgInfo(gb);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GroupDetails groupDetails(String loginID, String groupID) {
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		GroupDetails gd = queryFactory.select(
				Projections.bean(
						GroupDetails.class,
						qAddressGroup.groupId.as("groupID"),
						qAddressGroup.groupName,
						qAddressGroup.groupImg,
						qAddressGroup.groupDesc
						)
				)
		.from(qAddressGroup)
		.where(qAddressGroup.groupId.eq(groupID)).fetchOne();
		
		if (gd != null && !gd.getGroupID().equals("")) {
			List<Map> lm = new ArrayList<Map>();
			QUser qUser = QUser.user;
			QAddressGroupUser qAddressGroupUser = QAddressGroupUser.addressGroupUser;
			QUserNewAssist qUserNewAssist = QUserNewAssist.userNewAssist;
			
			List<User> lu = queryFactory.select(
					Projections.bean(
							User.class,
							qUser.userID,
							qUser.userName,
							new CaseBuilder()
								.when(qUserNewAssist.portrait_url.isNull())
								.then(qUser.userPic)
								.otherwise(qUserNewAssist.portrait_url).as("userPic")
							)
					)
					.from(qAddressGroupUser)
					.leftJoin(qUser).on(qAddressGroupUser.groupUser.eq(qUser.userID))
					.leftJoin(qUserNewAssist).on(qUserNewAssist.userid.eq(qUser.userID))
					.where(qAddressGroupUser.groupId.eq(groupID)).fetch();
			if (lu.size() > 0) {
				for (int i = 0; i < lu.size(); i++) {
					User u = (User) lu.get(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("userID", u.getUserID());
					map.put("userPic", u.getUserPic());
					map.put("userName", u.getUserName());
					lm.add(map);
				}
			}
			gd.setUser(lm);
		}
		return gd;
	}
	/**
	 * 复制文件
	 * @param fromFile
	 * @param toFile
	 * <br/>
	 * 2016年12月19日  下午3:31:50
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
	@Override
	public String getGroupUserIDs(String groupID) {
		return groupDao.getGroupUserIDs(groupID);
	}

	@Override
	public String getSeqAppGourpUser() {
		return utilDao.getSeqAppGourpUser();
	}
}
