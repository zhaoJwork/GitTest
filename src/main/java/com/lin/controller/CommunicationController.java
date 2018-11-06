package com.lin.controller;

import com.lin.domain.*;
import com.lin.service.*;
import com.lin.util.IntegralUtil;
import com.lin.util.JedisKey;
import com.lin.util.Result;
import com.lin.util.ValUtil;
import com.lin.vo.InputGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 通讯录入口
 * 
 * @author lwz
 * @date 2018.6.17
 */
@Api(description = "通讯录API")
@RestController
@RequestMapping("/communication")
public class CommunicationController {

	@Value("${application.pic_HttpIP}")
	private String picHttpIp ;
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


	@Autowired
	private AddressInfLogServiceImpl logServiceDsl;

	@Autowired
	private ValUtil val;

	/**
	 * @param loginID
	 *            登录人ID
	 * @return Result
	 * @author zhangweijie
	 * @date 2018.6.17
	 * @describe 描述 获取登录账号所属的常用分组列表 URL
	 *           请求实例：
	 *           localhost:8866/app-addresslist/communication/grouplist?loginID=130376&groupname=
	 */
	@ApiOperation(value="常用分组列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登录人", required = true, dataType = "String"),
			@ApiImplicitParam(name = "groupname", value = "分组名称" , dataType = "String")
	})
	@GetMapping("grouplist")
	public Result grouplist(HttpServletRequest req, String loginID,String groupname) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"自定义分组");
		Result result = new Result();
		if(val.valByT(result,log,"loginID",loginID)){return result;}
		try {
			List<Group> list = groupService.selectAllGroupByLoginID(loginID,groupname);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(list);
			logServiceDsl.saveAddressInfLog(log,result);
		} catch (Exception e) {
			logServiceDsl.saveError(e,log);
		}
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
	@ApiOperation(value="废弃")
	@GetMapping("userlist")
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
	@GetMapping("departmentlist")
	@ApiOperation(value="废弃")
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
	 *           http://localhost:8866/app-addresslist/communication/postlist?loginID=22295
	 */
	@ApiOperation(value="职务列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登录人", required = true, dataType = "String")
	})
	@GetMapping("postlist")
	public Result postlist(HttpServletRequest req, String loginID) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"职务");
		Result result = new Result();
		if(val.valByT(result,log,"loginID",loginID)){return result;}
		try {
			List<Position> list = postService.selectAllPosition();
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(list);
			logServiceDsl.saveAddressInfLog(log,result);
		} catch (Exception e) {
			logServiceDsl.saveError(e,log);
		}
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
	@GetMapping("userdetails")
	@ApiOperation(value="废弃")
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
	@GetMapping("userListDetails")
	@ApiOperation(value="废弃")
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
	 * @author
	 * @date 2018.6.17
	 * @describe 描述 获取登录账号所属的常用分组列表 URL
	 *           请求实例
	 *           http://127.0.0.1:8080/app-addresslist/communication/editGroup?
	 *           loginID=22295& groupID=& groupName=以小组分组& groupDesc=分组详情&
	 *           userIds=123_234_456& type=1增加人员 2 删除人员 3 删除组 返回实例
	 *           {"respCode":1,"respMsg":””,"respDesc":"正常返回数据"}
	 */
	@ApiOperation(value="编辑列表")
	@ApiImplicitParam(dataType = "InputGroup")
	@PostMapping("editGroup")
	public Result editGroup(HttpServletRequest req, @RequestBody InputGroup inputGroup) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"编辑分组");
		Result result = new Result();
		String loginID = inputGroup.getLoginID() == null ? "" : inputGroup.getLoginID();
		String groupID = inputGroup.getGroupID() == null ? "" : inputGroup.getGroupID();
		String groupName = inputGroup.getGroupName() == null ? "" : inputGroup.getGroupName();
		String groupDesc = inputGroup.getGroupDesc() == null ? "" : inputGroup.getGroupDesc();
		String userIds = inputGroup.getUserIds() == null ? "" : inputGroup.getUserIds();
		String type = inputGroup.getType() == null ? "" : inputGroup.getType();
		if(val.valByT(result,log,"loginID",loginID)){return result;}
		if (null == groupID || "".equals(groupID)) {
			if ("".equals(groupName) || "".equals(groupDesc) || "".equals(userIds)) {
				result.setRespCode("2");
				result.setRespDesc("新建组，请输入完整信息");
				logServiceDsl.saveAddressInfLog(log,result);
				return result;
			}
		} else {// 5001,5002,5003属于不可更改
			if (groupID.equals("5001") || groupID.equals("5002") || groupID.equals("5003")) {
				result.setRespDesc("分组不可修改");
				result.setRespCode("2");
				logServiceDsl.saveAddressInfLog(log,result);
				return result;
			}
		}
		try {
			groupService.editGroup(loginID, groupID, groupName, groupDesc, userIds, type, result);
			logServiceDsl.saveAddressInfLog(log,result);
		} catch (Exception e) {
			logServiceDsl.saveError(e,log);
		}
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
	@GetMapping("syncUser")
	@ApiOperation(value="废弃")
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
	@GetMapping("syncallOrganization")
	@ApiOperation(value="废弃")
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
	 *           请求实例
	 *           http://42.99.16.145/app-addresslist/communication/groupDetails?loginID=22295&groupID=1001
	 */
	@ApiOperation(value="分组详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登录人", required = true, dataType = "String"),
			@ApiImplicitParam(name = "groupID", value = "分组ID", required = true, dataType = "String")
	})
	@GetMapping("groupDetails")
	public Result groupDetails(HttpServletRequest req, String loginID, String groupID) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"分组详情");
		Result result = new Result();
		if(val.valByT(result,log,"loginID",loginID)){return result;}
		if (null == groupID || "".equals(groupID)) {
			result.setRespCode("2");
			result.setRespDesc("分组ID不能为空");
			logServiceDsl.saveAddressInfLog(log, result);
			return result;
		} else {
			if (groupID.equals("5001") || groupID.equals("5002") || groupID.equals("5003")) {
				result.setRespCode("2");
				result.setRespDesc("分组不可修改");
				logServiceDsl.saveAddressInfLog(log, result);
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
			logServiceDsl.saveAddressInfLog(log, result);
		} catch (Exception e) {
			logServiceDsl.saveError(e,log);
		}
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
	@GetMapping("replace")
	@ApiOperation(value="废弃")
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
	@ApiOperation(value="省份领域")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登录人", required = true, dataType = "String")
	})
	@GetMapping("organizationTerritory")
	public Result organizationTerritory(HttpServletRequest req, String loginID) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"省份领域");
		Result result = new Result();
		if(val.valByT(result,log,"loginID",loginID)){return result;}
		try {
			Map org = organizationService.getOrgName();
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(org);
			logServiceDsl.saveAddressInfLog(log, result);
		} catch (Exception e) {
			logServiceDsl.saveError(e,log);
		}
		return result;

	}

	/**
	 * 
	 * @return Result
	 * @author lwz
	 * @date 2017年9月4日
	 * @describe 获取50地市组织接口 URL
	 *           请求实例：
	 *           http://42.99.16.145/app-addresslist/communication/fiveCityOrganization?loginID=22295
	 */
	@ApiOperation(value="50地市")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登录人", required = true, dataType = "String"),
			@ApiImplicitParam(name = "pID", value = "上一级",  dataType = "String")
	})
	@GetMapping("fiveCityOrganization")
	public Result fiveCityOrganization(HttpServletRequest req, String loginID, String pID) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"50地市");
		Result result = new Result();
		if(val.valByT(result,log,"loginID",loginID)){return result;}
		try {
			pID = (pID == null || pID == "") ? "":pID;
			Map<String, Object> orgtreeMap = organizationService.fiveCityOrganization(pID);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(orgtreeMap);
			logServiceDsl.saveAddressInfLog(log, result);
		} catch (Exception e) {
			logServiceDsl.saveError(e,log);
		}
		return result;
	}

	/**
	 * 
	 * @return Result
	 * @author lwz
	 * @date 2017年9月15日
	 * @describe URL
	 *           请求实例：
	 *           http://42.99.16.145/app-addresslist/communication/userPower?loginID=22295
	 */
	@ApiOperation(value="用户权限")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginID", value = "当前登录人", required = true, dataType = "String")
	})
	@GetMapping("userPower")
	public Result userPower(HttpServletRequest req, String loginID) {
		AddressInfLog log =  logServiceDsl.getInfLog(req,"用户权限");
		Result result = new Result();
		try {
			UserPower up = new UserPower();
			up = userService.userPower(loginID);
			result.setRespCode("1");
			result.setRespDesc("正常返回数据");
			result.setRespMsg(up);
			logServiceDsl.saveAddressInfLog(log, result);
		} catch (Exception e) {
			logServiceDsl.saveError(e,log);
		}
		return result;
	}
	@PostMapping("userOrderNum")
	@ApiOperation(value="废弃")
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
