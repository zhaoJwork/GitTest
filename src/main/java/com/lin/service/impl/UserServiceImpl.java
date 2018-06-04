package com.lin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lin.util.JsonUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.mapper.UserMapper;
import com.lin.mapper.UtilMapper;
import com.lin.domain.ContextVo;
import com.lin.domain.FieldVo;
import com.lin.domain.Privilege;
import com.lin.domain.Role;
import com.lin.domain.User;
import com.lin.domain.UserDetails;
import com.lin.domain.UserOrder;
import com.lin.domain.UserPower;
import com.lin.service.RedisServiceI;
import com.lin.service.UserServiceI;
import com.lin.util.JedisKey;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Service("userService")
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserMapper userDao;
	
	@Autowired
	private UtilMapper utilDao;

	@Autowired
	private RedisServiceI jedisService;
	
	@Override
	public List<User> selectUserByFilter(HashMap<String, Object> paras) {
		List<User> list=userDao.selectUserByFilter(paras);
		return list;
	}

	/**
	 * 1、判断时间参数 如果等于 缺省时间： 2008-08-08 12:13:14 为首次同步
	 * 		获取缓存中的数据，
	 * 		存在数据，返回给前台人员集合和缓存中时间轴
	 * 		不存在数据，查询数据库中全部数据，放入缓存，新建时间轴放入缓存 返回给前台人员集合和时间轴
	 * 2、参数不等于  缺省时间：2008-08-08 12:13:14 	
	 * 		时间参数与缓存中时间轴比较是否相等
	 * 			相等：前台数据为最新数据，不需要更新返回null；
	 * 			不相等：根据时间查询数据库中修改时间大于此时间的数据，返回给前台这些增量数据，和缓存中的时间轴
	 * @author zhangweijie
	 * 			
	 */
	@Override
	public Map<String, Object> getSyncUser(String userDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Jedis jedis = new Jedis(PropUtil.JEDIS_HOSTNAME,Integer.parseInt(PropUtil.JEDIS_PORT));
		Map<String, Object> userMap=new HashMap<String,Object>();
		if(userDate.equals("2008-08-08 12:13:14")) {//userDate为空 返回redis中全部数据
			List<Object> redisUser=jedisService.values(JedisKey.USERKEY);
			if (redisUser == null||redisUser.size()==0) {// 缓存中没有
				User ud = new User();
				ud.setContext(userDate);
				List<User> userList=userDao.getNewDateByUpdateTime(ud);
				Map<String, String> map=new HashMap<String,String>();
				for(User u:userList) {
					//map.put(u.getUserID(),JSON.toJSONString(u, SerializerFeature.WriteNullStringAsEmpty));
					try {
						map.put(u.getUserID(), JsonUtil.toJson((u)));
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
				//System.err.println("*****************************************"+date+"*******************************");
				if(map.size()>0) {
					jedisService.save(JedisKey.USERKEY, map);
					String date=sdf.format(new Date());
					jedisService.save(JedisKey.USERKEY_DATE, date);
					System.err.println("*****************************************"+date+"*******************************");
					userMap.put("userList",userList);
					userMap.put("userDate",date);
				}
			}else {//缓存中 存在数据
				List<User> list=new ArrayList<User>();
				User uuu=new User();
				for(Object o:redisUser) {
					list.add(JsonUtil.fromJson(o.toString(),uuu.getClass()));
				}
				
				Collections.sort(list);
				
				userMap.put("userList",list);
				userMap.put("userDate",jedisService.getValue(JedisKey.USERKEY_DATE));
			}
			return userMap;
		}else {//userDate不为空 与redis中时间作比较
			String userRedisUpdate=jedisService.getValue(JedisKey.USERKEY_DATE);//取出redis中的时间
			if(userDate.equals(userRedisUpdate)) {//时间相等，没有需要更新的数据
				List<User> list=new ArrayList<User>();
				userMap.put("userList",list);
				userMap.put("userDate",jedisService.getValue(JedisKey.USERKEY_DATE));
				return userMap;
			}else {//时间不相等 根据userDate 查询增量数据 将userDate更新成现在redis中的时间
				User ud = new User();
				ud.setContext(userDate);
				List<User> userList=userDao.getNewDateByUpdateTime(ud);
				/*List<String> list=new ArrayList<String>();
				for(User u:userList) {
					list.add(JSON.toJSONString(u, SerializerFeature.WriteNullStringAsEmpty));
				}*/
				userMap.put("userDate", userRedisUpdate);
				userMap.put("userList",userList);
				return userMap;
			}
		}
	}

	/**
	 * 五、个人资料详情查询  
	 * @author hzh
	 * @time 2017-08-19
	 *
	 */
	@Override
	public UserDetails SelectUserDetailsById(String userID) {
		UserDetails ud =userDao.SelectUserDetails(userID);
		if(null != ud){			
			List<ContextVo> contList = userDao.selectContextList(userID);
			List<FieldVo> fieldList = userDao.selectFieldList(userID);
			ud.setContext(contList);
			ud.setField(fieldList);
		}
		
		return ud;
	}
	
	@Override
	public UserDetails SelectUserDetails(String userID) {
		return userDao.SelectUserDetails(userID);
	}

	/**
	 * 个人资料修改保存
	 * @author hzh
	 * @time 201-08-19
	 */
	@Override
	public void updateAdduserById(String userID, String organizationID,
			String postID, String phone, String email,String address,String typpe,String loginID,String flag,String contexts,String fields) {
		UserDetails user = new UserDetails();
		user.setUserID(userID);
		user.setOrganizationID(organizationID);
		user.setPostID(postID);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		user.setTyppe(typpe);
		user.setRowId(flag);
		user.setContexts(contexts);
		user.setFields(fields);
		userDao.updateAddUser(user);
		
		//为1时加入黑名单 为2时移除黑名单
		if(typpe == "1" || "1".equals(typpe)){
			user.setLoginID(loginID);
			String rowID = utilDao.getSeqAppUserWork();
			user.setRowId(rowID);
			userDao.insertBlack(user);
		}
		if(typpe == "2" || "2".equals(typpe)){
			userDao.deleteBlack(userID);
		}
	}

	/**
	 * 个人资料修改保存
	 * 删除人员的工作内容
	 * @author hzh
	 * @time 201-08-19
	 */
	@Override
	public void deleteContextById(String userID) {
		userDao.deleteContext(userID);
		
	}

	/**
	 * 个人资料修改保存
	 * 删除人员的擅长领域
	 * @author hzh
	 * @time 2017-08-19
	 */
	@Override
	public void deleteFieldById(String userID) {
		userDao.deleteField(userID);
		
	}

	/**
	 * 个人资料修改保存
	 * 保存工作内容和擅长领域
	 * @author hzh
	 * @time 2017-08-19
	 */
	@Override
	public void insertContextAndFieldVo(String userID, String context,
			String field) {
		//保存工作内容个人
		if(null != context && !"".equals(context)){
			String[] cont = context.split("_");
			List<ContextVo> listCV = new ArrayList<ContextVo>();
			for (int i = 0 ;i < cont.length ; i ++){
				ContextVo cv = new ContextVo();
				String contextId = cont[i];
				String rowID = utilDao.getSeqAppUserWork();
				cv.setRowId(rowID);
				cv.setContextID(contextId);
				cv.setUserID(userID);
				listCV.add(cv);
			}
			userDao.saveContextById(listCV);
		}
		//保存个人领域
		if(null != field && !"".equals(field)){
			String[] fie = field.split("_");
			List<FieldVo> listFV = new ArrayList<FieldVo>();
			for (int i = 0 ;i < fie.length ; i ++){
				FieldVo fv = new FieldVo();
				String fieldId = fie[i];
				String rowID = utilDao.getSeqAppUserTerritory();
				fv.setRowId(rowID);
				fv.setFieldID(fieldId);
				fv.setUserID(userID);
				listFV.add(fv);
			}
			userDao.saveFieldById(listFV);
		}
	}

	@Override
	public User SelectuserupdateById(String userID) {
		
		User user = userDao.getuserupdateById(userID);
		
		return user;
	}
	
	/**
	 * 权限接口
	 */
	public UserPower userPower(String userID){
		UserPower up = new UserPower(); 
		up  = userDao.userPower(userID);
		List<Privilege> listp = new ArrayList<Privilege>();
		List<Role> listr = new ArrayList<Role>();
		listp = userDao.getPrivilege(userID);
		listr = userDao.getRole(userID);
		if(listp != null && listp.size() > 0)
			up.setPrivilegeList(listp);
		if(listr != null && listr.size() > 0)
			up.setRoleList(listr);
		return up;
	}

	@Override
	public List<String> selectContextsByIds(List<String> contentIdsList) {
		return userDao.selectContextsByIds(contentIdsList);
	}

	@Override
	public List<String> selectFiledsByIds(List<String> fieldIdsList) {
		return userDao.selectFiledsByIds(fieldIdsList);
	}

	@Override
	public void orderNum(String staffid, Integer num) {
		//1、通过staffid查出这个人所在的部门的所有人。
		List<UserOrder> users = userDao.selectUsersByStaffid(staffid);
		//获取要排放位置的序号
		int orderNum = users.get(num - 1).getNew_order_num();
		//获取当前人的序号
		int currentNum = userDao.selectNewOrderNumByStaffid(staffid);
		//将此人从后向前调整
		if(currentNum > orderNum){
			//判断是否排放在第一位
			if(num == 1){
				//得到第一个人的序号
				int thisNum = users.get(num-1).getNew_order_num();
				//将第一个人的排序序号给排序的这个人
				userDao.updateThisUser(staffid,thisNum);
				//将这个部门中除排序的人之外序号分别加1
				for (int i = num-1 ; i < users.size() ; i ++){
					UserOrder user = users.get(i);
					//排序的人不再更新
					if(!staffid.equals(user.getUser_id())){
						userDao.updateThisUser(user.getUser_id(),user.getNew_order_num()+1);
					}
				}
			}else{
				//num > 1是排序规则
				int thisNum = users.get(num-1).getNew_order_num();
				int beforeNum = users.get(num-2).getNew_order_num();
				//2、遍历出第num-1和第num人，获取这两个人的orderNum
				int differNum = thisNum - beforeNum;
				//3、将第num-1人的orderNum值加1赋给staffid这个人。
				userDao.updateThisUser(staffid,beforeNum + 1 );
				//4、等于1将第num人的orderNum的值赋给staffid这个人，第num及其之后人的orderNum依次加1
				if(differNum ==1){
					//获取要跟新序号的人
					for (int i = num-1 ; i < users.size() ; i ++){
						UserOrder user = users.get(i);
						//排序的人不再更新
						if(!staffid.equals(user.getUser_id())){
							userDao.updateThisUser(user.getUser_id(),user.getNew_order_num()+1);
						}
					}
				}
			}
		}else if(currentNum < orderNum){//将此人从前向后调整
			//判断是否排放到最后一位
			if(num == users.size()){
				//得到最后一个人的序号
				int lastNum = users.get(num-1).getNew_order_num();
				//将最后一个人的排序序号+1给排序的这个人
				userDao.updateThisUser(staffid,lastNum + 1);
			}else{
				//num > 1是排序规则
				int thisNum = users.get(num).getNew_order_num();
				int beforeNum = users.get(num-1).getNew_order_num();
				//2、遍历出第num-1和第num人，获取这两个人的orderNum
				int differNum = thisNum - beforeNum;
				//3、将第num-1人的orderNum值加1赋给staffid这个人。
				userDao.updateThisUser(staffid,beforeNum + 1 );
				//4、等于1将第num人的orderNum的值赋给staffid这个人，第num及其之后人的orderNum依次加1
				if(differNum ==1){
					//获取要跟新序号的人
					for (int i = num ; i < users.size() ; i ++){
						UserOrder user = users.get(i);
						//排序的人不再更新
						if(!staffid.equals(user.getUser_id())){
							userDao.updateThisUser(user.getUser_id(),user.getNew_order_num()+1);
						}
					}
				}
			}
		}
	}

	@Override
	public List<UserOrder> selectUsersByStaffid(String staffid) {
		return userDao.selectUsersByStaffid(staffid);
	}
}
