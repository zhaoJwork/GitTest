package com.lin.dao;

import java.util.List;

import com.lin.domain.Group;
import com.lin.domain.GroupBean;
import com.lin.domain.GroupDetails;
import com.lin.domain.GroupUserBean;
import com.lin.domain.User;

/**
 * GroupDao
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface GroupDaoI {

	List<Group> selectAllGroupByLoginID(String loginID);
	/**
	 * 获取新分组ID
	 * @return
	 */
	String getNGroupID();
	
	/**
	 * 保存个人分组
	 * @param loginID
	 * @param groupID
	 * @param groupName
	 * @param groupDesc
	 */
	void saveGroup(GroupBean gb);
	
	/**
	 * 修改分组 
	 * @param gb
	 */
	void updateGroup(GroupBean gb);
	
	/**
	 * 删除分组
	 * @param listGU
	 */
	void deleteGroup(String groupID);
	
	/**
	 * 保存分组人员
	 * @param listGU
	 */
	void saveGroupUser(List<GroupUserBean> listGU);
	/**
	 * 删除分组人员
	 * @param listGU
	 */
	void deleteGroupUser(GroupUserBean gu);
	
	/**
	 * 获取组信息
	 * @return
	 */
	GroupDetails selectGroupBygroupID(String groupID);
	
	/**
	 * 获取组内人员
	 * @return
	 */
	List<User> selectGroupUserBygroupID(String groupID);
	/**
	 * @param groupID
	 * @param groupImgAddress void
	 * @author zhangWeiJie
	 * @date 2017年11月2日
	 * @describe 更新群组头像地址信息
	 */
	void updateGroupImgInfo(String groupID, String groupImgAddress);
	
	
	/**
	 * 获取组内所有人员
	 * @param groupID
	 * @return
	 */
	String getGroupUserIDs(String groupID);
	
}