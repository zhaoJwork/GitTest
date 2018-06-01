package com.lin.service;

import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Map;

import com.lin.domain.User;
import com.lin.domain.UserDetails;
import com.lin.domain.UserOrder;
import com.lin.domain.UserPower;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface UserServiceI {

	List<User> selectUserByFilter(HashMap<String, Object> paras);

	Map<String, Object> getSyncUser(String userDate);

	/**
	 * 个人资料详情  
	 * @param userID
	 * @return
	 * @author hzh
	 * @time 2017-08-19
	 */
	UserDetails SelectUserDetailsById(String userID);
	UserDetails SelectUserDetails(String userID);
    /**
     * 个人资料修改保存
     * @author hzh
     * @return 
	 * @time 2017-08-19
     */
	 void updateAdduserById(String userID, String organizationID,String postID, String phone, String email,String address,String typpe,String loginID,String flag,String contexts,String fields);
	 /**
	     * 个人资料修改保存
	     * 删除人员的工作内容
	     * @author hzh
	     * @return 
		 * @time 2017-08-19
	     */
	void deleteContextById(String userID);
	 /**
     * 个人资料修改保存
     * 删除人员的擅长领域
     * @author hzh
     * @return 
	 * @time 2017-08-19
     */
	void deleteFieldById(String userID);
	/**
	 * 个人资料修改保存
	 * 保存工作内容和擅长领域
	 * @param userID
	 * @param context
	 * @param field
	 */
	void insertContextAndFieldVo(String userID, String context, String field);

	User SelectuserupdateById(String userID);

	UserPower userPower(String userID);

	List<String> selectContextsByIds(List<String> contentIdsList);

	List<String> selectFiledsByIds(List<String> fieldIdsList);
	/**
	 * 通讯录人员排序
	 * 
	 * @param staffid
	 * @param num
	 */
	void orderNum(String staffid,Integer num);
	
	/**
	 * 根据人获取这个人所在部门的所有人
	 * 
	 * @param paras
	 * @return
	 */
	List<UserOrder> selectUsersByStaffid(String staffid);
	
	
}
