package com.lin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lin.dao.GroupDaoI;
import com.lin.domain.User;
import com.lin.service.RedisServiceI;
import com.lin.util.JedisKey;
import com.lin.util.Result;

/**
 * TODO
 * 
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Controller
@RequestMapping("/testJson")
public class TestJsonController {

	@Autowired
	private RedisServiceI jedisService;

	// http://localhost:8080/app-addresslist/testJson/tt?name=aaa&pwd=bbb
	@RequestMapping("tt")
	@ResponseBody
	public Result test(String name, String pwd) {
		Result result = new Result();
		result.setRespCode("2");
		result.setRespDesc("cuowu");
		if (name != null && pwd != null) {
			if (name.equals("aaa") && pwd.equals("bbb")) {
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

				result.setRespCode("1");
				result.setRespDesc("chenggong");
			}
		}
		return result;
	}
	
	
	// http://localhost:8080/app-addresslist/testJson/ttuser?name=aaa&pwd=bbb
		@RequestMapping("ttuser")
		@ResponseBody
		public Result testuser(String name, String pwd) {
			Result result = new Result();
			result.setRespCode("2");
			result.setRespDesc("cuowu");
			if (name != null && pwd != null) {
				if (name.equals("aaa") && pwd.equals("bbb")) {
					jedisService.delete(JedisKey.USERKEY);
					jedisService.delete(JedisKey.USERKEY_DATE);
					result.setRespCode("1");
					result.setRespDesc("chenggong");
				}
			}
			return result;
		}
}
