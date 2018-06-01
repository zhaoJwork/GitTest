package com.lin.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lin.domain.ContextVo;
import com.lin.domain.FieldVo;
import com.lin.domain.Privilege;
import com.lin.domain.Role;
import com.lin.domain.User;
import com.lin.domain.UserDetails;
import com.lin.domain.UserOrder;
import com.lin.domain.UserPower;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface UserDaoI {

	/**
	 * 
	 * @param search
	 * @param groupID
	 * @param organizationID
	 * @param provinceID
	 * @param fields
	 * @return List<User>
	 * @author zhangWeiJie
	 * @date 2017年8月19日
	 * @describe 查询分组
	 */
	List<User> selectUserByFilter(HashMap<String, Object> paras);

	/**
	 * 
	 * @param userUpdatedate
	 * @return List<User>
	 * @author zhangWeiJie
	 * @date 2017年8月19日
	 * @describe 查询新修改或增加的人员
	 */
	List<User> getNewDateByUpdateTime(User userUpdatedate);


	/**
	 * 五、个人资料详情查询  
	 * @author hzh
	 * @time 2017-08-19
	 *
	 */
	UserDetails SelectUserDetails(String userID);
	/**
	 * 五、个人资料详情查询 工作内容
	 * @author hzh
	 * @time 2017-08-19
	 *
	 */
	List<ContextVo> selectContextList(String userID);
	/**
	 * 五、个人资料详情查询  擅长领域 
	 * @author hzh
	 * @time 2017-08-19
	 *
	 */
	List<FieldVo> selectFieldList(String userID);
	/**
	 * 个人资料修改保存
	 * @param userID
	 * @param organizationID
	 * @param postID
	 * @param phone
	 * @param email
	 * @author hzh
	 * @time 2017-08-19
	 */
	void updateAddUser(UserDetails user);
	/**
	 * 个人资料修改保存
	 * 删除人员的工作内容
	 * @param userID
	 * @author hzh
	 * @time 2017-08-19
	 */
	void deleteContext(String userID);
	/**
	 * 个人资料修改保存
	 * 删除人员的擅长领域
	 * @param userID
	 * @author hzh
	 * @time 2017-08-19
	 */
	void deleteField(String userID);
	/**
	 * 保存个人工作内容
	 * @param listCV
	 */
	void saveContextById(List<ContextVo> listCV);
	/**
	 * 保存个人擅长领域
	 * @param listFV
	 */
	void saveFieldById(List<FieldVo> listFV);

	User getuserupdateById(String userID);

	/**
	 * 加入黑名单
	 * @param user
	 */
	void insertBlack(UserDetails user);

	/**
	 * 从黑名单中移除
	 * @param userID
	 */
	void deleteBlack(String userID);
	/**
	 * 获取权限树
	 * @param userID
	 * @return
	 */
	UserPower userPower(String userID);
	/**
	 * 权限
	 * @param userID
	 * @return
	 */
	List<Privilege> getPrivilege(String userID);
	/**
	 * 角色
	 * @param userID
	 * @return
	 */
	List<Role> getRole(String userID);
	/**
	 * @param contentIdsList
	 * @return List<String>
	 * @author zhangWeiJie
	 * @date 2017年10月31日
	 * @describe 根据ID查询工作内容名称
	 */
	List<String> selectContextsByIds(List<String> contentIdsList);

	/**
	 * @param fieldIdsList
	 * @return List<String>
	 * @author zhangWeiJie
	 * @date 2017年10月31日
	 * @describe 根据Id查询出擅长领域名称
	 */
	List<String> selectFiledsByIds(List<String> fieldIdsList);
	/**
	 * 根据人获取这个人所在部门的所有人
	 * 
	 * @param paras
	 * @return
	 */
	List<UserOrder> selectUsersByStaffid(String staffid);
	
	/**
	 * 修改这个人的排序
	 * 
	 * @param staffid
	 * @param orderNum
	 * @return
	 */
	int updateThisUser(@Param("staffid")String staffid,@Param("orderNum")int orderNum);
	
	/**
	 * 通过staffid获取这个人的newOrderNum
	 * 
	 * @param staffid
	 * @return
	 */
	int selectNewOrderNumByStaffid(String staffid); 
}
