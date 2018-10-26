package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.AddressGroupRepository;
import com.lin.repository.AddressGroupUserRepository;
import com.lin.util.ImageUtil;
import com.lin.util.Result;
import com.lin.vo.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 *  即时通讯调用通讯录群聊分组
 *  @author  lwz
 *  @date 2018.10.26
 */
@Service("GroupChatService")
@Transactional(rollbackFor = Exception.class)
public class GroupChatService extends AbstractService<AddressGroup,String>{

	@Autowired
	public GroupChatService(AddressGroupRepository addressGroupRepository) {
		super(addressGroupRepository);
	}

	@Resource
	private AddressGroupRepository addressGroupRepository;
	@Resource
	private AddressGroupUserRepository addressGroupUserRepository;

	/**
	 * 创建讨论组
	 * @return
	 */
	public void createGroup(Result result, InCreateGroup inCreateGroup){
		////保存主表数据
		AddressGroup addressGroup = new AddressGroup();
		String groupID = getSeq()+"";
		addressGroup.setRowId(groupID);
		addressGroup.setGroupId(groupID);
		addressGroup.setCreateDate(new Date());
		addressGroup.setUpdateDate(new Date());
		addressGroup.setCreateUser(inCreateGroup.getCrator());
		addressGroup.setGroupName(inCreateGroup.getGroupName());
		addressGroup.setGroupDesc(inCreateGroup.getGroupName());
		addressGroup.setGroupImg(inCreateGroup.getAvatar());
		addressGroup.setGroupChatID(inCreateGroup.getId());
		addressGroupRepository.save(addressGroup);
		List<AddressGroupUser> listgu = new ArrayList<AddressGroupUser>();
		for (int i = 0; i < inCreateGroup.getJoinUsers().size(); i++) {
				AddressGroupUser addressGroupUser = new AddressGroupUser();
				addressGroupUser.setRowId(getSeq()+"");
				addressGroupUser.setGroupId(groupID);
				addressGroupUser.setGroupUser(inCreateGroup.getJoinUsers().get(i).getCustomerId());
				addressGroupUser.setCreateDate(new Date());
				listgu.add(addressGroupUser);
		}
		addressGroupUserRepository.saveAll(listgu);
		result.setRespCode("1");
		result.setRespDesc("正常返回数据");
	}

	/**
	 * 添加成员
	 * @return
	 */
	public void inviteFriend(Result result, InInviteFriend inviteFriend){
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		List<AddressGroupUser> listgu = new ArrayList<AddressGroupUser>();
		if(0 < inviteFriend.getMembers().size()){
			for (int i = 0; i < inviteFriend.getMembers().size(); i++) {
				String groupChatID = inviteFriend.getMembers().get(i).getGroupId();
				AddressGroup addressGroup = jpaQueryFactory().select(qAddressGroup).from(qAddressGroup).where(qAddressGroup.groupChatID.eq(groupChatID)).fetchOne();
				if(null != addressGroup){
					AddressGroupUser addressGroupUser = new AddressGroupUser();
					addressGroupUser.setRowId(getSeq()+"");
					addressGroupUser.setGroupId(addressGroup.getGroupId());
					addressGroupUser.setGroupUser(inviteFriend.getMembers().get(i).getCustomerId());
					addressGroupUser.setCreateDate(new Date());
					listgu.add(addressGroupUser);
				}
			}
			addressGroupUserRepository.saveAll(listgu);
		}
		result.setRespCode("1");
		result.setRespDesc("正常返回数据");
	}

	/**
	 * 删除成员
	 * @return
	 */
	public void removeMembers(Result result, InRemoveMembers inRemoveMembers){
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		QAddressGroupUser qAddressGroupUser = QAddressGroupUser.addressGroupUser;
		List<AddressGroupUser> listgu = new ArrayList<AddressGroupUser>();
		AddressGroup addressGroup = jpaQueryFactory().select(qAddressGroup).from(qAddressGroup).where(qAddressGroup.groupChatID.eq(inRemoveMembers.getGroupId())).fetchOne();
		String[] customerIds = inRemoveMembers.getCustomerIds().split(",");
		if(null != addressGroup) {
			if (0 < customerIds.length) {
				for (int i = 0; i < customerIds.length; i++) {
					String customerId = customerIds[i];
					jpaQueryFactory().delete(qAddressGroupUser)
							.where(qAddressGroupUser.groupId.eq(addressGroup.getGroupId()).and(qAddressGroupUser.groupUser.eq(customerId)))
							.execute();
				}
			}
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
		}else{
			result.setRespCode("1");
			result.setRespDesc("分组不存在");
		}
	}

	/**
	 * 群头像更新
	 * @return
	 */
	public void updateGroupInfo(Result result, InUpdateGroupInfo inUpdateGroupInfo){
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		jpaQueryFactory().update(qAddressGroup).set(qAddressGroup.groupImg,inUpdateGroupInfo.getAvatar())
				.where(qAddressGroup.groupChatID.eq(inUpdateGroupInfo.getGroupId()))
				.execute();
		result.setRespCode("1");
		result.setRespDesc("正常返回数据");
	}

	/**
	 * 退出群组
	 * @return
	 */
	public void exitGroup(Result result, InExitGroup inExitGroup){
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		QAddressGroupUser qAddressGroupUser = QAddressGroupUser.addressGroupUser;
		AddressGroup addressGroup = jpaQueryFactory().select(qAddressGroup).from(qAddressGroup).where(qAddressGroup.groupChatID.eq(inExitGroup.getGroupId())).fetchOne();
		if(null != addressGroup) {
			jpaQueryFactory().delete(qAddressGroupUser)
					.where(qAddressGroupUser.groupId.eq(addressGroup.getGroupId()).and(qAddressGroupUser.groupUser.eq(inExitGroup.getCustomerId())))
					.execute();
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
		}else{
			result.setRespCode("1");
			result.setRespDesc("分组不存在");
		}
	}

	/**
	 * 解散群组
	 * @return
	 */
	public void dissolution(Result result, InDissolution inDissolution){
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		QAddressGroupUser qAddressGroupUser = QAddressGroupUser.addressGroupUser;
		AddressGroup addressGroup = jpaQueryFactory().select(qAddressGroup).from(qAddressGroup)
				.where(qAddressGroup.groupChatID.eq(inDissolution.getGroupId())).fetchOne();
		if(null != addressGroup) {
			jpaQueryFactory().delete(qAddressGroupUser)
					.where(qAddressGroupUser.groupId.eq(addressGroup.getGroupId()))
					.execute();
			jpaQueryFactory().delete(qAddressGroup)
					.where(qAddressGroup.groupId.eq(addressGroup.getGroupId()))
					.execute();
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
		}else{
			result.setRespCode("1");
			result.setRespDesc("分组不存在");
		}
	}

	/**
	 * 群头像更新
	 * @return
	 */
	public void modify(Result result, InModify inModify){
		QAddressGroup qAddressGroup = QAddressGroup.addressGroup;
		jpaQueryFactory().update(qAddressGroup)
				.set(qAddressGroup.groupName,inModify.getGroupName())
				.set(qAddressGroup.updateDate,new Date())
				.where(qAddressGroup.groupChatID.eq(inModify.getId()))
				.execute();
		result.setRespCode("1");
		result.setRespDesc("正常返回数据");
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
