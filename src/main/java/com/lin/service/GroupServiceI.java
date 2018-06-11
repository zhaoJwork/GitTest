package com.lin.service;

import java.util.List;

import com.lin.domain.Group;
import com.lin.domain.GroupDetails;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface GroupServiceI {

	List<Group> selectAllGroupByLoginID(String loginID,String groupname);
	
	void editGroup(String loginID,String groupID,String groupName,String groupDesc,String userIds,String type) throws Exception;
	
	GroupDetails groupDetails(String loginID,String groupID);

	String getGroupUserIDs(String groupID);

	String getSeqAppGourpUser();
}
