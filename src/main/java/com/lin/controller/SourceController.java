package com.lin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lin.service.RedisServiceI;
import com.lin.service.SourceServiceI;
import com.lin.service.UserServiceI;
import com.lin.util.JedisKey;
import com.lin.util.Result;

/**
 * TODO
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Controller
@RequestMapping("/sourceController")
public class SourceController {

	@Autowired
	private RedisServiceI jedisService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private SourceServiceI sourceService;
	/**
	 * 数据维护页面
	 * http://localhost:8080/app-addresslist/index2.jsp
	 * @param radio1
	 * @param context
	 * @param username
	 * @param userpwd
	 * @return
	 */
	@RequestMapping("myTest")
	@ResponseBody
	public Result testuserdate(String radio1, String context,
			String username,String userpwd) {
		Result result = new Result();
		result.setRespCode("2");
		result.setRespDesc("无效 ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		try {
			if(username.equals("aaa") && userpwd.equals("ccc")){
				//刷新rdies人员时间
				if(radio1.equals("1")){
					/** 
					 * 此处刷新的是组织人数以及在线人数涉及到的表:appuser.address_organization
					 * 刷新在线人数的和离线人数的状态--appuser.address_user
					 */
					sourceService.refurbishUserCount();
					jedisService.delete(JedisKey.USERKEY_DATE);
					jedisService.save(JedisKey.USERKEY_DATE,sdf.format(d));
				//刷新rdies人员基础数据	
				}else if(radio1.equals("2")){
					jedisService.delete(JedisKey.USERKEY);//app-addresslist-USERKEYs
					String userDate = "2008-08-08 12:13:14";
					/**
					 * 操作人员表,将人员信息放到缓存中,涉及到的表:appuser.address_user
					 */
					userService.getSyncUser(userDate);
				//刷新rdies组织部门	
				}else if(radio1.equals("3")){
					/**
					 * 清理组织部门缓存方法
					 */
					delOrganization();
				//添加黑名单	
				}else if(radio1.equals("4")){
					sourceService.addBlackUser(context);
					jedisService.delete(JedisKey.USERKEY_DATE);
					jedisService.save(JedisKey.USERKEY_DATE,sdf.format(d));
				//移除黑名单	
				}else if(radio1.equals("5")){
					sourceService.delBlackUser(context);
					jedisService.delete(JedisKey.USERKEY_DATE);
					jedisService.save(JedisKey.USERKEY_DATE,sdf.format(d));
				}
				result.setRespCode("1");
				result.setRespDesc("成功 ");
			}else{
				result.setRespCode("2");
				result.setRespDesc("用户密码错误 ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setRespCode("3");
			result.setRespDesc("异常:"+e.getMessage());
		}

		return result;
	}

	/**
	 * 清理组织部门
	 * @return
	 */
	private void delOrganization(){
		jedisService.delete(JedisKey.ORGANIZATIONKEY);
		jedisService.delete(JedisKey.ORGANIZATIONKEY_DATE);
		jedisService.delete(JedisKey.FIVEORGANIZATIONKEY);

		jedisService.delete(JedisKey.UPORGANIZATIONKEY);
		jedisService.delete(JedisKey.UPORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.JTORGANIZATIONKEY);
		jedisService.delete(JedisKey.JTORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.ZYORGANIZATIONKEY);
		jedisService.delete(JedisKey.ZYORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.AHORGANIZATIONKEY);
		jedisService.delete(JedisKey.AHORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.BJORGANIZATIONKEY);
		jedisService.delete(JedisKey.BJORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.CQORGANIZATIONKEY);
		jedisService.delete(JedisKey.CQORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.FJORGANIZATIONKEY);
		jedisService.delete(JedisKey.FJORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.GDORGANIZATIONKEY);
		jedisService.delete(JedisKey.GDORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.GSORGANIZATIONKEY);
		jedisService.delete(JedisKey.GSORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.GXORGANIZATIONKEY);
		jedisService.delete(JedisKey.GXORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.GZORGANIZATIONKEY);
		jedisService.delete(JedisKey.GZORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.HLJORGANIZATIONKEY);
		jedisService.delete(JedisKey.HLJORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.HBORGANIZATIONKEY);
		jedisService.delete(JedisKey.HBORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.HEORGANIZATIONKEY);
		jedisService.delete(JedisKey.HEORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.HNORGANIZATIONKEY);
		jedisService.delete(JedisKey.HNORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.HAORGANIZATIONKEY);
		jedisService.delete(JedisKey.HAORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.HNAORGANIZATIONKEY);
		jedisService.delete(JedisKey.HNAORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.JXORGANIZATIONKEY);
		jedisService.delete(JedisKey.JXORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.JLORGANIZATIONKEY);
		jedisService.delete(JedisKey.JLORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.JSORGANIZATIONKEY);
		jedisService.delete(JedisKey.JSORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.LNORGANIZATIONKEY);
		jedisService.delete(JedisKey.LNORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.NXORGANIZATIONKEY);
		jedisService.delete(JedisKey.NXORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.NMGORGANIZATIONKEY);
		jedisService.delete(JedisKey.NMGORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.QHORGANIZATIONKEY);
		jedisService.delete(JedisKey.QHORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.SHORGANIZATIONKEY);
		jedisService.delete(JedisKey.SHORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.SXORGANIZATIONKEY);
		jedisService.delete(JedisKey.SXORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.SDORGANIZATIONKEY);
		jedisService.delete(JedisKey.SDORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.SIORGANIZATIONKEY);
		jedisService.delete(JedisKey.SIORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.SCORGANIZATIONKEY);
		jedisService.delete(JedisKey.SCORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.TJORGANIZATIONKEY);
		jedisService.delete(JedisKey.TJORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.XJORGANIZATIONKEY);
		jedisService.delete(JedisKey.XJORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.XZORGANIZATIONKEY);
		jedisService.delete(JedisKey.XZORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.YNORGANIZATIONKEY);
		jedisService.delete(JedisKey.YNORGANIZATIONKEY_DATE);

		jedisService.delete(JedisKey.ZJORGANIZATIONKEY);
		jedisService.delete(JedisKey.ZJORGANIZATIONKEY_DATE);
	}
	
}
