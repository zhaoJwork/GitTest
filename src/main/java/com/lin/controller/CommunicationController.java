package com.lin.controller;

import com.lin.util.JsonUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lin.domain.AddressInfLogBean;
import com.lin.domain.Department;
import com.lin.domain.Group;
import com.lin.domain.GroupDetails;
import com.lin.domain.Organization;
import com.lin.domain.Position;
import com.lin.domain.User;
import com.lin.domain.UserDetails;
import com.lin.domain.UserOrder;
import com.lin.domain.UserPower;
import com.lin.service.AddressInfLogServiceI;
import com.lin.service.DepartmentServiceI;
import com.lin.service.GroupServiceI;
import com.lin.service.OrganizationServiceI;
import com.lin.service.PostServiceI;
import com.lin.service.RedisServiceI;
import com.lin.service.UserServiceI;
import com.lin.util.IntegralUtil;
import com.lin.util.JedisKey;
import com.lin.util.Result;

/**
 * 通讯录入口
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Controller
@RequestMapping("/communication")
public class CommunicationController {

	@Value("${application.pic_HttpIP}")
	private String picHttpIp;
	@Autowired
  private IntegralUtil integralUtil;
	@Autowired
	private DepartmentServiceI departmentService;
	@Autowired
	private GroupServiceI groupService;
	@Autowired
	private PostServiceI postService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private OrganizationServiceI organizationService;
	@Autowired
	private AddressInfLogServiceI logService;
	@Autowired
	private RedisServiceI jedisService;

	/**
	 * @param loginID
	 *            登录人ID
	 * @return Result
	 * @author zhangweijie
	 * @date 2017年8月18日
	 * @describe 描述 获取登录账号所属的常用分组列表 URL
	 *           http://127.0.0.1:8080/app-addresslist/communication/grouplist
	 *           请求实例：
	 *           http://127.0.0.1:8080/app-addresslist/communication/grouplist?loginID=22295
	 *           localhost:8866/app-addresslist/communication/grouplist?loginID=130376&groupname=
	 *           返回实例：
	 *           {"respCode":"1","respDesc":"正常返回数据","respMsg":[{"groupName":"各省分管领导","groupID":"111"},{"groupName":"各省政企主任","groupID":"222"},{"groupName":"重点50个本地网","groupID":"333"},{"groupName":"小组","groupID":"444"}]}
	 * 
	 */
	@RequestMapping("grouplist")
	@ResponseBody
	public Result grouplist(HttpServletRequest req, String loginID,String groupname) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "获取分组");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		try {
			List<Group> list = groupService.selectAllGroupByLoginID(loginID,groupname);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * @param loginID 登录人ID 
	 * 		  search 模糊查询 
	 * 		  groupID 组ID 
	 * 		  organizationID 部门ID 
	 * 		  provinceID 省份ID 
	 * 		  fieldID 擅长领域多值以下划线分割
	 * @return Result
	 * @author zhangweijie
	 * @date 2017年8月18日
	 * @describe 描述 获取全部人员列表 URL
	 *           http://127.0.0.1:8080/app-addresslist/communication/userlist
	 *           请求实例：
	 *           http://127.0.0.1:8080/app-addresslist/communication/userlist?loginID=22295$&groupID=5002
	 *           返回实例：
	 *           {"respCode":"1","respDesc":"正常返回数据","respMsg":[{"field":"互联网、物联网","userID":"123","provinceID":"12345","phone":"15900000000","post":"大客户经理","userPic":"/pic.pic","address":"北京","email":"15900000000@qq.com","context":"联系大客户、大客户维护","organizationID":"12345","userName":"小明","type":"行政主任"},{"field":"互联网、物联网","userID":"123","provinceID":"12345","phone":"15900000000","post":"大客户经理","userPic":"/pic.pic","address":"北京","email":"15900000000@qq.com","context":"联系大客户、大客户维护","organizationID":"12345","userName":"小明","type":"行政主任"}]}
	 * 
	 */
	@RequestMapping("userlist")
	@ResponseBody
	public Result userlist(HttpServletRequest req, String loginID, String search, String groupID, String organizationID,
			String provinceID, String fieldID) {
	  //User.picHttpIp = picHttpIp;
		AddressInfLogBean log = logService.getAddressInfLog(req, "人员列表");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		HashMap<String, Object> paras = new HashMap<String, Object>();
		String field = "";
		if (fieldID != null && !fieldID.trim().equals("")) {
			String[] fields = fieldID.split("_");
			for (String f : fields) {
				field += ",'" + f + "'";
			}
			field = field.substring(1);
			paras.put("cou", fields.length);
		}
		String provinceIDs = "";
		if (provinceID != null && !provinceID.trim().equals("")) {
			String[] provinceIDstr = provinceID.split("_");
			for (String f : provinceIDstr) {
				provinceIDs += ",'" + f + "'";
			}
			provinceIDs = provinceIDs.substring(1);
		}
		if (search != null && search.equals("%")) {
			result.setRespCode("2");
			result.setRespDesc("参数不合法");
			result.setRespMsg("");
			return result;
		}
		paras.put("search", search);
		paras.put("groupID", groupID);
		paras.put("organizationID", organizationID);
		paras.put("provinceID", provinceIDs);
		paras.put("fields", field);
		try {
			List<User> list = userService.selectUserByFilter(paras);
 			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author
	 * @date 2017年8月18日
	 * @describe 描述 获取人员部门列表 URL
	 *           http://127.0.0.1:8080/app-addresslist/communication/departmentlist?loginID=22295
	 * 
	 *           请求实例
	 *           127.0.0.1:8080/app-addresslist/communication/departmentlist?loginID=22295&organizationID=1956144
	 *           返回实例
	 *           {"respCode":"1","respDesc":"正常返回数据","respMsg":[{"departmentName":"运营部","departmentID":"111"},{"departmentName":"政企部","departmentID":"222"}]}
	 * 
	 */
	@RequestMapping("departmentlist")
	@ResponseBody
	public Result departmentlist(HttpServletRequest req, String loginID, String organizationID) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "获取部门");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (organizationID == null || organizationID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("organizationID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}

		try {
			List<Department> list = departmentService.selectAllDepartment(organizationID);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author
	 * @date 2017年8月18日
	 * @describe 描述 获取职务列表 URL
	 *           http://127.0.0.1:8080/app-addresslist/communication/postlist?loginID=22295
	 */
	@RequestMapping("postlist")
	@ResponseBody
	public Result postlist(HttpServletRequest req, String loginID) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "获取职务");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}

		try {
			List<Position> list = postService.selectAllPosition();
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author hzh
	 * @date 2017年8月18日
	 * @describe 描述 获取登录账号所属的常用分组列表
	 *           http://127.0.0.1:8080/app-addresslist/communication/userdetails?loginID=101948&userID=101948
	 */
	@RequestMapping("userdetails")
	@ResponseBody
	public Result userdetails(HttpServletRequest req, String loginID, String userID) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "获取人员");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (userID == null || userID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("人员ID不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}else{
			UserDetails  uu = userService.SelectUserDetails(userID);
			if(uu == null){
				//此处注意,体验没问题,生产有问题,缺少此处代码
				result.setRespCode("2");
				result.setRespDesc("该用户不在通讯录中,暂不提供人员信息");
				logService.saveAddressInfLog(log, result);
				return result;
			}else{
				try {
					UserDetails ud = userService.SelectUserDetailsById(userID);
					result.setRespCode("1");
					result.setRespDesc("正常返回数据");
					result.setRespMsg(ud);
				} catch (Exception e) {
					e.printStackTrace();
					log.setExpError(e.toString());
					result.setRespCode("2");
					result.setRespDesc("失败");
					result.setRespMsg("");
				}
			}
		}
		logService.saveAddressInfLog(log, result);
		return result;
		}
	/**
	 * 
	 * @return Result
	 * @author hzh
	 * @date 2017年8月18日
	 * @describe 描述 获取登录账号所属的常用分组列表
	 *           http://127.0.0.1:8080/app-addresslist/communication/userListDetails?loginID=101948&userID=101948
	 */
	@RequestMapping("userListDetails")
	@ResponseBody
	public Result userListDetails(String loginID, String userIDs) {
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			return result;
		}
		if (userIDs == null || userIDs.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("userIDs 不能为空");
			return result;
		}
		String[] ids = userIDs.split(",");
		List<UserDetails> userDetailsList = new ArrayList<UserDetails>(); 
		try{	
			for (String userID : ids) {
				UserDetails ud = userService.SelectUserDetailsById(userID);
				if(ud == null){
					UserDetails u =  new UserDetails();
					u.setUserID(userID);
					u.setAddress("");
					u.setContexts("");
					u.setEmail("");
					u.setInstall(0);
					u.setOrganizationID("");
					u.setOrganizationName("");
					u.setPhone("");
					u.setPostID("");
					u.setPostName("");
					u.setRowId("");
					u.setTyppe("");
					u.setUserName("");
					u.setUserPic("");
					userDetailsList.add(u);
				}else{
					userDetailsList.add(ud);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		result.setRespCode("1");
		result.setRespDesc("正常返回数据");
		result.setRespMsg(userDetailsList);
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author HZH
	 * @date 2017年8月18日
	 * @describe 描述 获取登录账号所属的常用分组列表 URL http://127.0.0.1:8080/app-addresslist/
	 *           请求实例
	 * 
	 *           返回实例 {"respCode":1,"respMsg":””,"respDesc":"正常返回数据"}
	 *           http://127.0.0.1:8080/app-addresslist/communication/adduser?loginID=22295&userID=23&organizationID=123&posted=234&phone=15900000000&email=15900000000@qq.com&context=23_45_56&field=123_234_456
	 */
	@RequestMapping("adduser")
	@ResponseBody
	public Result adduser(HttpServletRequest req, String loginID, String userID, // 人员ID
			String organizationID, // 部门ID
			String postID, // 职务ID
			String phone, // 电话
			String email, // 邮箱
			String context, // 工作内容（_分割传递）
			String field, // 擅长领域（_分割传递）
			String typpe) { // 偷偷加的，用来加入人员黑名单
		AddressInfLogBean log = logService.getAddressInfLog(req, "修改人员");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (userID == null || userID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("userID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		try {
			String flag = "2";
			String address = null;
			if (typpe == null || "".equals(typpe)) {
				UserDetails uds = userService.SelectUserDetails(userID);
				address = uds.getAddress();
				int i = address.lastIndexOf("/");
				address = address.substring(0, i);
				i = address.lastIndexOf("/");
				address = address.substring(i) + "/";
				if (organizationID != null && !"".equals(organizationID)) {
					Organization organ = organizationService.getOrganizationByOrganid(organizationID);
					address = uds.getAddress().replace(address, "/" + organ.getOrganizationName() + "/");
				} else {
					address = "";
				}
				if (postID != null && "".equals(postID)) {
					String postName = postService.getPositionNameByID(postID);
					if (postName.equals("部门经理") || postName.equals("公司领导") || postName.equals("政企领导")) {
						flag = "1";
					}
				}
				/*
				 * 先删除工作内容和擅长领域 再插入
				 */
				userService.deleteContextById(userID); // 删除人员的工作内容
				userService.deleteFieldById(userID); // 删除人员的擅长领域
				/*
				 * 保存人员工作内容和擅长领域
				 */
				userService.insertContextAndFieldVo(userID, context, field);
			}
			// 查出擅长领域和工作内容，拼接字符传，保存用户信息
			String contextNames = "";
			String fieldNames = "";
			List<String> contentIdsList = new ArrayList<String>();
			Collections.addAll(contentIdsList, context.split("_"));
			List<String> contentsList = userService.selectContextsByIds(contentIdsList);
			contextNames = listToString(contentsList);

			List<String> fieldIdsList = new ArrayList<String>();
			Collections.addAll(fieldIdsList, field.split("_"));
			List<String> filedsList = userService.selectFiledsByIds(fieldIdsList);
			fieldNames = listToString(filedsList);

			userService.updateAdduserById(userID, organizationID, postID, phone, email, address, typpe, loginID, flag,
					contextNames, fieldNames);

			/*
			 * 获取当前修改人的个人信息 供下面的redis更新使用
			 */
			User ud = userService.SelectuserupdateById(userID);

			jedisService.save(JedisKey.USERKEY, userID, JsonUtil.toJson(ud)); // 更新redis
																				// userID的key
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(new Date());
			jedisService.save(JedisKey.USERKEY_DATE, date);

			result.setRespCode("1");
			result.setRespDesc("正常保存数据");
			result.setRespMsg("");
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	private String listToString(List<String> contentsList) {
		String result = "";
		for (String s : contentsList) {
			result += s + ",";
		}
		if (!result.equals("") && result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * 
	 * @author
	 * @date 2017年8月18日
	 * @describe 描述 获取登录账号所属的常用分组列表 URL
	 *           http://127.0.0.1:8080/app-addresslist/communication/editGroup
	 * 
	 *           请求实例
	 *           http://127.0.0.1:8080/app-addresslist/communication/editGroup?
	 *           loginID=22295& groupID=& groupName=以小组分组& groupDesc=分组详情&
	 *           userIds=123_234_456& type=1增加人员 2 删除人员 3 删除组 返回实例
	 *           {"respCode":1,"respMsg":””,"respDesc":"正常返回数据"}
	 * 
	 */
	@RequestMapping("editGroup")
	@ResponseBody
	public Result editGroup(HttpServletRequest req, String loginID, String groupID, String groupName, String groupDesc,
			String userIds, String type) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "编辑分组");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (null == groupID || "".equals(groupID)) {
			if ("".equals(groupName) || "".equals(groupDesc) || "".equals(userIds)) {
				result.setRespCode("2");
				result.setRespDesc("新建组，请输入完整信息");
				logService.saveAddressInfLog(log, result);
				return result;
			}
		} else {// 5001,5002,5003属于不可更改
			if (groupID.equals("5001") || groupID.equals("5002") || groupID.equals("5003")) {
				result.setRespCode("2");
				result.setRespDesc("分组不可修改");
				logService.saveAddressInfLog(log, result);
				return result;
			}
		}
		try {
			groupService.editGroup(loginID, groupID, groupName, groupDesc, userIds, type);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg("");
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author zhangweijie
	 * @date 2017年8月18日
	 * @describe 描述 同步所有人员， URL
	 *           http://127.0.0.1:8080/app-addresslist/communication/syncall?
	 * 
	 *           请求实例
	 *           http://127.0.0.1:8080/app-addresslist/communication/syncall?loginID=22295&
	 *           userDate=2017-01-01 12:12:12&orgtreeDate=2017-01-01 12:12:12
	 *           返回实例
	 *           {"respCode":"1","respDesc":"正常返回数据","respMsg":{"organization":[{"userCount":"100","flag":"2","pID":"111","organizationName":"集团总部","organizationID":"111","onlineCount":"10","type":"1"},{"userCount":"13","flag":"1","pID":"111","organizationName":"北京","organizationID":"121","onlineCount":"12","type":"1"},{"userCount":"5","flag":"1","pID":"111","organizationName":"上海","organizationID":"123","onlineCount":"3","type":"1"}],"user":[{"field":"物联网，互联网","userID":"111","provinceID":"123","phone":"15900000000","post":"大客户经理","userPic":"/pig.pig","address":"北京","email":"15900000000@qq.com","context":"联系大客户，联系小客户","organizationID":"123","userName":"张三","type":"1"},{"field":"物联网，互联网","userID":"111","provinceID":"123","phone":"15900000000","post":"大客户经理","userPic":"/pig.pig","address":"北京","email":"15900000000@qq.com","context":"联系大客户，联系小客户","organizationID":"123","userName":"李四","type":"1"},{"field":"物联网，互联网","userID":"111","provinceID":"123","phone":"15900000000","post":"大客户经理","userPic":"/pig.pig","address":"北京","email":"15900000000@qq.com","context":"联系大客户，联系小客户","organizationID":"123","userName":"王五","type":"1"}]}}
	 * 
	 *           userDate，orgtreeDate 如果为空则返回全部数据
	 */
	// @RequestMapping("syncall")
	// @ResponseBody
	public Result syncall(HttpServletRequest req, String loginID, String userDate, String organizationDate) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "同步数据");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (userDate == null || userDate.trim().equals("")) {
			userDate = "2008-08-08 12:13:14";
		}
		if (organizationDate == null || organizationDate.trim().equals("")) {
			organizationDate = "2008-08-08 12:13:14";
		}
		try {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			// Map<String, Object> userMap=userService.getSyncUser(userDate);
			Map<String, Object> orgtreeMap = organizationService.getSyncOrganization(organizationDate, "");
			// resultMap.put("user", userMap);
			resultMap.put("organization", orgtreeMap);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * @param req
	 * @param loginID
	 * @param userDate
	 * @return Result
	 * @author zhangWeiJie
	 * @date 2017年8月25日
	 * @describe 同步人员接口
	 *           testUrl:localhost:8080/app-addresslist/communication/syncUser?loginID=22295&userDate=
	 * 
	 */
	@RequestMapping("syncUser")
	@ResponseBody
	public Result syncUser(HttpServletRequest req, String loginID, String userDate) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "同步人员");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (userDate == null || userDate.trim().equals("")) {
			userDate = "2008-08-08 12:13:14";
		} else {
			userDate = userDate.substring(0, 10) + " " + userDate.substring(10);
		}

		try {
			Map<String, Object> userMap = userService.getSyncUser(userDate);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(userMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * @param req
	 * @param loginID
	 * @param organizationDate
	 * @return Result
	 * @author zhangWeiJie
	 * @date 2017年8月25日
	 * @describe 同步组织机构接口
	 *           testUrl:localhost:8080/app-addresslist/communication/syncallOrganization?loginID=22295&organizationDate=
	 */
	@RequestMapping("syncallOrganization")
	@ResponseBody
	public Result syncallOrganization(HttpServletRequest req, String loginID, String organizationDate,
			String organizationID) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "同步组织");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (organizationID == null || organizationID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("organizationID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (organizationDate == null || organizationDate.trim().equals("")) {
			organizationDate = "2008-08-08 12:13:14";
		} else {
			organizationDate = organizationDate.substring(0, 10) + " " + organizationDate.substring(10);
		}
		try {
			Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
			orgtreeMap = organizationService.getSyncOrganization(organizationDate, organizationID);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(orgtreeMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		/*
		 * 添加积分  活跃指数
		 * 营销通报	点击进入	每日一次
		 */
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("StaffId", loginID);
			map.put("BusiCode", "020");
			int num  = organizationService.getCountIntegralByDay(map);
			if(num ==0){
        integralUtil.addScore(loginID, "020", "进入通讯录模块","0");
				System.out.println("----进入通讯录模块积分添加成功----");
			}
		}catch(Exception e){
			System.out.println("----添加积分Exception----");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author lwz
	 * @date 2017年8月20日
	 * @describe 描述 常用分组详情 URL
	 *           http://42.99.16.145/app-addresslist/communication/groupDetails
	 *           请求实例
	 *           http://42.99.16.145/app-addresslist/communication/groupDetails?loginID=22295&groupID=1001
	 *           返回实例 {"respCode":1,"respMsg":””,"respDesc":"正常返回数据"}
	 *           http://127.0.0.1:8080/app-addresslist/communication/groupDetails?loginID=22295&groupID=5007
	 */
	@RequestMapping("groupDetails")
	@ResponseBody
	public Result groupDetails(HttpServletRequest req, String loginID, String groupID) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "分组详情");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		if (null == groupID || "".equals(groupID)) {
			result.setRespCode("2");
			result.setRespDesc("分组ID不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		} else {
			if (groupID.equals("5001") || groupID.equals("5002") || groupID.equals("5003")) {
				result.setRespCode("2");
				result.setRespDesc("分组不可修改");
				logService.saveAddressInfLog(log, result);
				return result;
			}
		}
		try {// 查询群组信息
			GroupDetails gd = groupService.groupDetails(loginID, groupID);
			// 完整的图片下载地址
			gd.setGroupImg(picHttpIp + gd.getGroupImg());
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(gd);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * @param organizationDate
	 * @param userDate
	 * @return Result
	 * @author zhangWeiJie
	 * @date 2017年8月25日
	 * @describe 查询是否有需要更新的数据，如果organizationDate和userDate都为空 则返回无更新
	 *           如果时间与缓存中时间相同则说明不需要更新 否则需要更新
	 *           localhost:8080/app-addresslist/communication/replace?loginID=00&organizationDate=99&userDate=88
	 */
	@RequestMapping("replace")
	@ResponseBody
	public Result replace(String loginID, String organizationDate, String userDate) {
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			return result;
		}
		try {
			result.setRespCode("1");
			result.setRespDesc("正常响应");
			Map<String, String> rMap = new HashMap<String, String>();

			if (organizationDate == null || organizationDate.trim().equals("")) {
				rMap.put("organizationType", "0");
			} else {
				String oDate = jedisService.getValue(JedisKey.ORGANIZATIONKEY_DATE);
				if (organizationDate.equals(oDate)) {
					rMap.put("organizationType", "0");
				} else {
					rMap.put("organizationType", "1");
				}
			}
			if (userDate == null || userDate.trim().equals("")) {
				rMap.put("userType", "0");
			} else {
				String uDate = jedisService.getValue(JedisKey.USERKEY_DATE);
				if (userDate.equals(uDate)) {
					rMap.put("userType", "0");
				} else {
					rMap.put("userType", "1");
				}
			}
			result.setRespMsg(rMap);

		} catch (Exception e) {
			result.setRespCode("2");
			result.setRespDesc("查询失败，服务错误");
		}
		return result;
	}

	/**
	 * 省份领域标签
	 * 
	 * @author hzh
	 * @time 2017-08-24
	 * @param req
	 * @return result
	 *         http://127.0.0.1:8080/app-addresslist/communication/organizationTerritory?loginID=22295
	 */
	@RequestMapping("organizationTerritory")
	@ResponseBody
	public Result organizationTerritory(HttpServletRequest req, String loginID) {

		AddressInfLogBean log = logService.getAddressInfLog(req, "省份领域");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		try {
			Map org = organizationService.getOrgName();
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(org);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;

	}

	/**
	 * 
	 * @return Result
	 * @author lwz
	 * @date 2017年9月4日
	 * @describe 获取50地市组织接口 URL
	 *           http://42.99.16.145/app-addresslist/communication/fiveCityOrganization?loginID=22295
	 *           请求实例：
	 *           http://42.99.16.145/app-addresslist/communication/fiveCityOrganization?loginID=22295
	 *           返回实例：
	 *           http://127.0.0.1:8080/app-addresslist/communication/fiveCityOrganization?loginID=22295
	 */
	@RequestMapping("fiveCityOrganization")
	@ResponseBody
	public Result fiveCityOrganization(HttpServletRequest req, String loginID) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "50地市");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		try {
			Map<String, Object> orgtreeMap = organizationService.fiveCityOrganization(loginID);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(orgtreeMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author lwz
	 * @date 2017年9月15日
	 * @describe URL
	 *           http://42.99.16.145/app-addresslist/communication/userPower?loginID=22295
	 *           请求实例：
	 *           http://42.99.16.145/app-addresslist/communication/userPower?loginID=22295
	 *           返回实例：
	 *           http://127.0.0.1:8080/app-addresslist/communication/userPower?loginID=22295
	 */
	@RequestMapping("userPower")
	@ResponseBody
	public Result userPower(HttpServletRequest req, String loginID) {
		AddressInfLogBean log = logService.getAddressInfLog(req, "权限");
		Result result = new Result();
		if (loginID == null || loginID.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("loginID 不能为空");
			logService.saveAddressInfLog(log, result);
			return result;
		}
		try {
			UserPower up = new UserPower();
			up = userService.userPower(loginID);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(up);
		} catch (Exception e) {
			e.printStackTrace();
			log.setExpError(e.toString());
			result.setRespCode("2");
			result.setRespDesc("失败");
			result.setRespMsg("");
		}
		logService.saveAddressInfLog(log, result);
		return result;
	}
	@RequestMapping("userOrderNum")
	@ResponseBody
	public Result userOrderNum(String staffid,Integer num){
		Result result = new Result();
		if (staffid == null || staffid.trim().equals("")) {
			result.setRespCode("2");
			result.setRespDesc("staffid 不能为空");
			return result;
		}
		if (num == null || num.equals(0)) {
			result.setRespCode("2");
			result.setRespDesc("num 不能为空");
			return result;
		}
		try {
			List<UserOrder> users = userService.selectUsersByStaffid(staffid);
			int size = users.size();
			int numValue = num.intValue();
			if(numValue < 0){
				result.setRespCode("2");
				result.setRespDesc("排序序号为负数，不合理排序！");
				return result;
			}
			if(numValue > size){
				result.setRespCode("2");
				result.setRespDesc("排序序号超出部门人数，不合理排序！");
				return result;
			}
			
			userService.orderNum(staffid,num);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
		} catch (Exception e) {
			e.printStackTrace();
			result.setRespCode("2");
			result.setRespDesc("失败");
		}
		return result;
	}
}
