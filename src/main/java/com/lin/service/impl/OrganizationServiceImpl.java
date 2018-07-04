package com.lin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lin.domain.OrganizationBean;
import com.lin.util.JsonUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lin.mapper.OrganizationMapper;
import com.lin.domain.OrgTer;
import com.lin.service.OrganizationServiceI;
import com.lin.service.RedisServiceI;
import com.lin.util.JedisKey;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 组织机构service 实现类
 * 
 * @author zhangWeiJie
 * @date 2017年8月19日
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationServiceI {

	@Autowired
	private OrganizationMapper organizationDao;

	@Autowired
	private RedisServiceI jedisService;


	@Value("${application.redis_host}")
	private String redis_host;
	@Value("${application.redis_port}")
	private int redis_port;
	@Value("${application.redis_timeout}")
	private int redis_timeout;


	/**
	 * 1、判断时间参数 如果等于 缺省时间： 2008-08-08 12:13:14 为首次同步 获取缓存中的数据，
	 * 存在数据，返回给前台人员集合和缓存中时间轴 不存在数据，查询数据库中全部数据，放入缓存，新建时间轴放入缓存 返回给前台人员集合和时间轴
	 * 2、参数不等于 缺省时间：2008-08-08 12:13:14 时间参数与缓存中时间轴比较是否相等
	 * 相等：前台数据为最新数据，不需要更新返回null； 不相等：根据时间查询数据库中修改时间大于此时间的数据，返回给前台这些增量数据，和缓存中的时间轴
	 * 
	 * @author zhangweijie
	 * 
	 */
	@Override
	public Map<String, Object> getSyncOrganization(String orgtreeDate, String organizationID) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Jedis jedis = new
		// Jedis(PropUtil.JEDIS_HOSTNAME,Integer.parseInt(PropUtil.JEDIS_PORT));
		Map<String, Object> organizationMap = new HashMap<String, Object>();
		try {
			if (orgtreeDate.equals("2008-08-08 12:13:14")) {// organizationDate为空
															// 返回redis中全部数据
				List<Object> redisOrganization = new ArrayList<Object>();
				Date d = new Date();
				String jedisTime = "";
				if (organizationID.equals("0")) {
					organizationMap.put("name", "up");
					/*** 最上级组织缓存key */
					//redisOrganization = jedisService.values(JedisKey.UPORGANIZATIONKEY);
					if (redisOrganization == null || redisOrganization.size() == 0) {// 缓存中不存在
						organizationMap.put("name", "up");
						organizationMap.put("organizationDate", sdf.format(d));
						List<OrganizationBean> organizationList = organizationDao.organizationByPID(organizationID);
						Map<String, String> map = new LinkedHashMap<String, String>();
						for (OrganizationBean o : organizationList) {
							map.put(o.getOrganizationID(), JsonUtil.toJson(o));
						}
						if (map.size() > 0) {
							jedisService.save(JedisKey.UPORGANIZATIONKEY, map);
							jedisService.save(JedisKey.UPORGANIZATIONKEY_DATE, sdf.format(d));
							Collections.sort(organizationList);
							organizationMap.put("organizationList", organizationList);
						}
					} else {
						// 存在 则为首次同步数据 返回全部
						List<OrganizationBean> list = new ArrayList<OrganizationBean>();
						OrganizationBean ooo = new OrganizationBean();
						for (Object re : redisOrganization) {
							OrganizationBean organization = JsonUtil.fromJson(re.toString(), ooo.getClass());
							list.add(organization);
						}
						Collections.sort(list);
						organizationMap.put("organizationList", list);
						organizationMap.put("organizationDate", jedisService.getValue(JedisKey.UPORGANIZATIONKEY_DATE));
					}
				} else {
					if (organizationID.equals("1844641")) { // 1844641 集团总部
						organizationMap.put("name", "jt");
						redisOrganization = jedisService.values(JedisKey.JTORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.JTORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1844643")) { // 1844643
																	// 专业公司及运营单位
						organizationMap.put("name", "zy");
						redisOrganization = jedisService.values(JedisKey.ZYORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.ZYORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944646")) { // 1944646 安徽
						organizationMap.put("name", "ah");
						redisOrganization = jedisService.values(JedisKey.AHORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.AHORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944664")) { // 1944664 北京
						organizationMap.put("name", "bj");
						redisOrganization = jedisService.values(JedisKey.BJORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.BJORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944649")) { // 1944649 重庆
						organizationMap.put("name", "cq");
						redisOrganization = jedisService.values(JedisKey.CQORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.CQORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944645")) { // 1944645 福建
						organizationMap.put("name", "fj");
						redisOrganization = jedisService.values(JedisKey.FJORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.FJORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944641")) { // 1944641 广东
						organizationMap.put("name", "gd");
						redisOrganization = jedisService.values(JedisKey.GDORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.GDORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944652")) { // 1944652 甘肃
						organizationMap.put("name", "gs");
						redisOrganization = jedisService.values(JedisKey.GSORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.GSORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944647")) { // 1944647 广西
						organizationMap.put("name", "gx");
						redisOrganization = jedisService.values(JedisKey.GXORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.GXORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944659")) { // 1944659 贵州
						organizationMap.put("name", "gz");
						redisOrganization = jedisService.values(JedisKey.GZORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.GZORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944651")) { // 1944651
																	// 黑龙江
						organizationMap.put("name", "hlj");
						redisOrganization = jedisService.values(JedisKey.HLJORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.HLJORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944669")) { // 1944669 河北
						organizationMap.put("name", "hb");
						redisOrganization = jedisService.values(JedisKey.HBORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.HBORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944653")) { // 1944653 湖北
						organizationMap.put("name", "he");
						redisOrganization = jedisService.values(JedisKey.HEORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.HEORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944667")) { // 1944667 河南
						organizationMap.put("name", "hn");
						redisOrganization = jedisService.values(JedisKey.HNORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.HNORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944660")) { // 1944660 海南
						organizationMap.put("name", "ha");
						redisOrganization = jedisService.values(JedisKey.HAORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.HAORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944655")) { // 1944655 湖南
						organizationMap.put("name", "hna");
						redisOrganization = jedisService.values(JedisKey.HNAORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.HNAORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944650")) { // 1944650 江西
						organizationMap.put("name", "jx");
						redisOrganization = jedisService.values(JedisKey.JXORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.JXORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944658")) { // 1944658 吉林
						organizationMap.put("name", "jl");
						redisOrganization = jedisService.values(JedisKey.JLORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.JLORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944643")) { // 1944643 江苏
						organizationMap.put("name", "js");
						redisOrganization = jedisService.values(JedisKey.JSORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.JSORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944668")) { // 1944668 辽宁
						organizationMap.put("name", "ln");
						redisOrganization = jedisService.values(JedisKey.LNORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.LNORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944661")) { // 1944661 宁夏
						organizationMap.put("name", "nx");
						redisOrganization = jedisService.values(JedisKey.NXORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.NXORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944671")) { // 1944671
																	// 内蒙古
						organizationMap.put("name", "nmg");
						redisOrganization = jedisService.values(JedisKey.NMGORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.NMGORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944662")) { // 1944662 青海
						organizationMap.put("name", "qh");
						redisOrganization = jedisService.values(JedisKey.QHORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.QHORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944642")) { // 1944642 上海
						organizationMap.put("name", "sh");
						redisOrganization = jedisService.values(JedisKey.SHORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.SHORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944670")) { // 1944670 山西
						organizationMap.put("name", "sx");
						redisOrganization = jedisService.values(JedisKey.SXORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.SXORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944666")) { // 1944666 山东
						organizationMap.put("name", "sd");
						redisOrganization = jedisService.values(JedisKey.SDORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.SDORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944656")) { // 1944656 陕西
						organizationMap.put("name", "si");
						redisOrganization = jedisService.values(JedisKey.SIORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.SIORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944654")) { // 1944654 四川
						organizationMap.put("name", "sc");
						redisOrganization = jedisService.values(JedisKey.SCORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.SCORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944665")) { // 1944665 天津
						organizationMap.put("name", "tj");
						redisOrganization = jedisService.values(JedisKey.TJORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.TJORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944648")) { // 1944648 新疆
						organizationMap.put("name", "xj");
						redisOrganization = jedisService.values(JedisKey.XJORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.XJORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944663")) { // 1944663 西藏
						organizationMap.put("name", "xz");
						redisOrganization = jedisService.values(JedisKey.XZORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.XZORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944657")) { // 1944657 云南
						organizationMap.put("name", "yn");
						redisOrganization = jedisService.values(JedisKey.YNORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.YNORGANIZATIONKEY_DATE);
					} else if (organizationID.equals("1944644")) { // 1944644 浙江
						organizationMap.put("name", "zj");
						redisOrganization = jedisService.values(JedisKey.ZJORGANIZATIONKEY);
						jedisTime = jedisService.getValue(JedisKey.ZJORGANIZATIONKEY_DATE);
					}
					if (redisOrganization == null || redisOrganization.size() == 0) {// 缓存中不存在
						List<OrganizationBean> organizationList = organizationDao.downOrganization(organizationID);
						Map<String, String> map = new LinkedHashMap<String, String>();
						for (OrganizationBean o : organizationList) {
							map.put(o.getOrganizationID(), JsonUtil.toJson(o));
						}
						if (map.size() > 0) {
							if (organizationID.equals("1844641")) { // 1844641
																	// 集团总部
								jedisService.save(JedisKey.JTORGANIZATIONKEY, map);
								jedisService.save(JedisKey.JTORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1844643")) { // 1844643
																			// 专业公司及运营单位
								jedisService.save(JedisKey.ZYORGANIZATIONKEY, map);
								jedisService.save(JedisKey.ZYORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944646")) { // 1944646
																			// 安徽
								jedisService.save(JedisKey.AHORGANIZATIONKEY, map);
								jedisService.save(JedisKey.AHORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944664")) { // 1944664
																			// 北京
								jedisService.save(JedisKey.BJORGANIZATIONKEY, map);
								jedisService.save(JedisKey.BJORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944649")) { // 1944649
																			// 重庆
								jedisService.save(JedisKey.CQORGANIZATIONKEY, map);
								jedisService.save(JedisKey.CQORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944645")) { // 1944645
																			// 福建
								jedisService.save(JedisKey.FJORGANIZATIONKEY, map);
								jedisService.save(JedisKey.FJORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944641")) { // 1944641
																			// 广东
								jedisService.save(JedisKey.GDORGANIZATIONKEY, map);
								jedisService.save(JedisKey.GDORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944652")) { // 1944652
																			// 甘肃
								jedisService.save(JedisKey.GSORGANIZATIONKEY, map);
								jedisService.save(JedisKey.GSORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944647")) { // 1944647
																			// 广西
								jedisService.save(JedisKey.GXORGANIZATIONKEY, map);
								jedisService.save(JedisKey.GXORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944659")) { // 1944659
																			// 贵州
								jedisService.save(JedisKey.GZORGANIZATIONKEY, map);
								jedisService.save(JedisKey.GZORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944651")) { // 1944651
																			// 黑龙江
								jedisService.save(JedisKey.HLJORGANIZATIONKEY, map);
								jedisService.save(JedisKey.HLJORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944669")) { // 1944669
																			// 河北
								jedisService.save(JedisKey.HBORGANIZATIONKEY, map);
								jedisService.save(JedisKey.HBORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944653")) { // 1944653
																			// 湖北
								jedisService.save(JedisKey.HEORGANIZATIONKEY, map);
								jedisService.save(JedisKey.HEORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944667")) { // 1944667
																			// 河南
								jedisService.save(JedisKey.HNORGANIZATIONKEY, map);
								jedisService.save(JedisKey.HNORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944660")) { // 1944660
																			// 海南
								jedisService.save(JedisKey.HAORGANIZATIONKEY, map);
								jedisService.save(JedisKey.HAORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944655")) { // 1944655
																			// 湖南
								jedisService.save(JedisKey.HNAORGANIZATIONKEY, map);
								jedisService.save(JedisKey.HNAORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944650")) { // 1944650
																			// 江西
								jedisService.save(JedisKey.JXORGANIZATIONKEY, map);
								jedisService.save(JedisKey.JXORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944658")) { // 1944658
																			// 吉林
								jedisService.save(JedisKey.JLORGANIZATIONKEY, map);
								jedisService.save(JedisKey.JLORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944643")) { // 1944643
																			// 江苏
								jedisService.save(JedisKey.JSORGANIZATIONKEY, map);
								jedisService.save(JedisKey.JSORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944668")) { // 1944668
																			// 辽宁
								jedisService.save(JedisKey.LNORGANIZATIONKEY, map);
								jedisService.save(JedisKey.LNORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944661")) { // 1944661
																			// 宁夏
								jedisService.save(JedisKey.NXORGANIZATIONKEY, map);
								jedisService.save(JedisKey.NXORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944671")) { // 1944671
																			// 内蒙古
								jedisService.save(JedisKey.NMGORGANIZATIONKEY, map);
								jedisService.save(JedisKey.NMGORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944662")) { // 1944662
																			// 青海
								jedisService.save(JedisKey.QHORGANIZATIONKEY, map);
								jedisService.save(JedisKey.QHORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944642")) { // 1944642
																			// 上海
								jedisService.save(JedisKey.SHORGANIZATIONKEY, map);
								jedisService.save(JedisKey.SHORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944670")) { // 1944670
																			// 山西
								jedisService.save(JedisKey.SXORGANIZATIONKEY, map);
								jedisService.save(JedisKey.SXORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944666")) { // 1944666
																			// 山东
								jedisService.save(JedisKey.SDORGANIZATIONKEY, map);
								jedisService.save(JedisKey.SDORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944656")) { // 1944656
																			// 陕西
								jedisService.save(JedisKey.SIORGANIZATIONKEY, map);
								jedisService.save(JedisKey.SIORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944654")) { // 1944654
																			// 四川
								jedisService.save(JedisKey.SCORGANIZATIONKEY, map);
								jedisService.save(JedisKey.SCORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944665")) { // 1944665
																			// 天津
								jedisService.save(JedisKey.TJORGANIZATIONKEY, map);
								jedisService.save(JedisKey.TJORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944648")) { // 1944648
																			// 新疆
								jedisService.save(JedisKey.XJORGANIZATIONKEY, map);
								jedisService.save(JedisKey.XJORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944663")) { // 1944663
																			// 西藏
								jedisService.save(JedisKey.XZORGANIZATIONKEY, map);
								jedisService.save(JedisKey.XZORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944657")) { // 1944657
																			// 云南
								jedisService.save(JedisKey.YNORGANIZATIONKEY, map);
								jedisService.save(JedisKey.YNORGANIZATIONKEY_DATE, sdf.format(d));
							} else if (organizationID.equals("1944644")) { // 1944644
																			// 浙江
								jedisService.save(JedisKey.ZJORGANIZATIONKEY, map);
								jedisService.save(JedisKey.ZJORGANIZATIONKEY_DATE, sdf.format(d));
							}
							Collections.sort(organizationList);
							organizationMap.put("organizationDate", sdf.format(d));
							organizationMap.put("organizationList", organizationList);
						}
					} else {// 存在 则为首次同步数据 返回全部
						List<OrganizationBean> list = new ArrayList<OrganizationBean>();
						OrganizationBean ooo = new OrganizationBean();
						for (Object re : redisOrganization) {
							OrganizationBean organization = JsonUtil.fromJson(re.toString(), ooo.getClass());
							list.add(organization);
						}
						Collections.sort(list);
						organizationMap.put("organizationList", list);
						organizationMap.put("organizationDate", jedisTime);
					}
				}
			} else {// organizationDate不为空 与redis中时间作比较
				String jedisTime = "";
				Date d = new Date();
				List<Object> redisOrganization = new ArrayList<Object>();
				if (organizationID.equals("0")) {
					organizationMap.put("name", "up");
					/*** 最上级组织缓存key */
					redisOrganization = jedisService.values(JedisKey.UPORGANIZATIONKEY);
					if (redisOrganization == null || redisOrganization.size() == 0) {// 缓存中不存在
						organizationMap.put("name", "up");
						organizationMap.put("organizationDate", sdf.format(d));
						List<OrganizationBean> organizationList = organizationDao.organizationByPID(organizationID);
						Map<String, String> map = new LinkedHashMap<String, String>();
						for (OrganizationBean o : organizationList) {
							map.put(o.getOrganizationID(), JsonUtil.toJson(o));
						}
						if (map.size() > 0) {
							jedisService.save(JedisKey.UPORGANIZATIONKEY, map);
							jedisService.save(JedisKey.UPORGANIZATIONKEY_DATE, sdf.format(d));
							Collections.sort(organizationList);
							organizationMap.put("organizationList", organizationList);
						}
					} else {
						// 存在 则为首次同步数据 返回全部
						List<OrganizationBean> list = new ArrayList<OrganizationBean>();
						OrganizationBean ooo = new OrganizationBean();
						for (Object re : redisOrganization) {
							OrganizationBean organization = JsonUtil.fromJson(re.toString(), ooo.getClass());
							list.add(organization);
						}
						Collections.sort(list);
						organizationMap.put("organizationList", list);
						organizationMap.put("organizationDate", jedisService.getValue(JedisKey.UPORGANIZATIONKEY_DATE));
					}
				}

				if (organizationID.equals("1844641")) { // 1844641 集团总部
					organizationMap.put("name", "jt");
					redisOrganization = jedisService.values(JedisKey.JTORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.JTORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1844643")) { // 1844643
																// 专业公司及运营单位
					organizationMap.put("name", "zy");
					redisOrganization = jedisService.values(JedisKey.ZYORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.ZYORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944646")) { // 1944646 安徽
					organizationMap.put("name", "ah");
					redisOrganization = jedisService.values(JedisKey.AHORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.AHORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944664")) { // 1944664 北京
					organizationMap.put("name", "bj");
					redisOrganization = jedisService.values(JedisKey.BJORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.BJORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944649")) { // 1944649 重庆
					organizationMap.put("name", "cq");
					redisOrganization = jedisService.values(JedisKey.CQORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.CQORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944645")) { // 1944645 福建
					organizationMap.put("name", "fj");
					redisOrganization = jedisService.values(JedisKey.FJORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.FJORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944641")) { // 1944641 广东
					organizationMap.put("name", "gd");
					redisOrganization = jedisService.values(JedisKey.GDORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.GDORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944652")) { // 1944652 甘肃
					organizationMap.put("name", "gs");
					redisOrganization = jedisService.values(JedisKey.GSORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.GSORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944647")) { // 1944647 广西
					organizationMap.put("name", "gx");
					redisOrganization = jedisService.values(JedisKey.GXORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.GXORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944659")) { // 1944659 贵州
					organizationMap.put("name", "gz");
					redisOrganization = jedisService.values(JedisKey.GZORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.GZORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944651")) { // 1944651 黑龙江
					organizationMap.put("name", "hlj");
					redisOrganization = jedisService.values(JedisKey.HLJORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.HLJORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944669")) { // 1944669 河北
					organizationMap.put("name", "hb");
					redisOrganization = jedisService.values(JedisKey.HBORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.HBORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944653")) { // 1944653 湖北
					organizationMap.put("name", "he");
					redisOrganization = jedisService.values(JedisKey.HEORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.HEORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944667")) { // 1944667 河南
					organizationMap.put("name", "hn");
					redisOrganization = jedisService.values(JedisKey.HNORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.HNORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944660")) { // 1944660 海南
					organizationMap.put("name", "ha");
					redisOrganization = jedisService.values(JedisKey.HAORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.HAORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944655")) { // 1944655 湖南
					organizationMap.put("name", "hna");
					redisOrganization = jedisService.values(JedisKey.HNAORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.HNAORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944650")) { // 1944650 江西
					organizationMap.put("name", "jx");
					redisOrganization = jedisService.values(JedisKey.JXORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.JXORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944658")) { // 1944658 吉林
					organizationMap.put("name", "jl");
					redisOrganization = jedisService.values(JedisKey.JLORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.JLORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944643")) { // 1944643 江苏
					organizationMap.put("name", "js");
					redisOrganization = jedisService.values(JedisKey.JSORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.JSORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944668")) { // 1944668 辽宁
					organizationMap.put("name", "ln");
					redisOrganization = jedisService.values(JedisKey.LNORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.LNORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944661")) { // 1944661 宁夏
					organizationMap.put("name", "nx");
					redisOrganization = jedisService.values(JedisKey.NXORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.NXORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944671")) { // 1944671 内蒙古
					organizationMap.put("name", "nmg");
					redisOrganization = jedisService.values(JedisKey.NMGORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.NMGORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944662")) { // 1944662 青海
					organizationMap.put("name", "qh");
					redisOrganization = jedisService.values(JedisKey.QHORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.QHORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944642")) { // 1944642 上海
					organizationMap.put("name", "sh");
					redisOrganization = jedisService.values(JedisKey.SHORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.SHORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944670")) { // 1944670 山西
					organizationMap.put("name", "sx");
					redisOrganization = jedisService.values(JedisKey.SXORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.SXORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944666")) { // 1944666 山东
					organizationMap.put("name", "sd");
					redisOrganization = jedisService.values(JedisKey.SDORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.SDORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944656")) { // 1944656 陕西
					organizationMap.put("name", "si");
					redisOrganization = jedisService.values(JedisKey.SIORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.SIORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944654")) { // 1944654 四川
					organizationMap.put("name", "sc");
					redisOrganization = jedisService.values(JedisKey.SCORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.SCORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944665")) { // 1944665 天津
					organizationMap.put("name", "tj");
					redisOrganization = jedisService.values(JedisKey.TJORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.TJORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944648")) { // 1944648 新疆
					organizationMap.put("name", "xj");
					redisOrganization = jedisService.values(JedisKey.XJORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.XJORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944663")) { // 1944663 西藏
					organizationMap.put("name", "xz");
					redisOrganization = jedisService.values(JedisKey.XZORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.XZORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944657")) { // 1944657 云南
					organizationMap.put("name", "yn");
					redisOrganization = jedisService.values(JedisKey.YNORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.YNORGANIZATIONKEY_DATE);
				} else if (organizationID.equals("1944644")) { // 1944644 浙江
					organizationMap.put("name", "zj");
					redisOrganization = jedisService.values(JedisKey.ZJORGANIZATIONKEY);
					jedisTime = jedisService.getValue(JedisKey.ZJORGANIZATIONKEY_DATE);
				}
				if (redisOrganization == null || redisOrganization.size() == 0) {// 缓存中不存在
					List<OrganizationBean> organizationList = organizationDao.downOrganization(organizationID);
					Map<String, String> map = new LinkedHashMap<String, String>();
					for (OrganizationBean o : organizationList) {
						map.put(o.getOrganizationID(), JsonUtil.toJson(o));
					}
					if (map.size() > 0) {
						if (organizationID.equals("1844641")) { // 1844641 集团总部
							jedisService.save(JedisKey.JTORGANIZATIONKEY, map);
							jedisService.save(JedisKey.JTORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1844643")) { // 1844643
																		// 专业公司及运营单位
							jedisService.save(JedisKey.ZYORGANIZATIONKEY, map);
							jedisService.save(JedisKey.ZYORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944646")) { // 1944646
																		// 安徽
							jedisService.save(JedisKey.AHORGANIZATIONKEY, map);
							jedisService.save(JedisKey.AHORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944664")) { // 1944664
																		// 北京
							jedisService.save(JedisKey.BJORGANIZATIONKEY, map);
							jedisService.save(JedisKey.BJORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944649")) { // 1944649
																		// 重庆
							jedisService.save(JedisKey.CQORGANIZATIONKEY, map);
							jedisService.save(JedisKey.CQORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944645")) { // 1944645
																		// 福建
							jedisService.save(JedisKey.FJORGANIZATIONKEY, map);
							jedisService.save(JedisKey.FJORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944641")) { // 1944641
																		// 广东
							jedisService.save(JedisKey.GDORGANIZATIONKEY, map);
							jedisService.save(JedisKey.GDORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944652")) { // 1944652
																		// 甘肃
							jedisService.save(JedisKey.GSORGANIZATIONKEY, map);
							jedisService.save(JedisKey.GSORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944647")) { // 1944647
																		// 广西
							jedisService.save(JedisKey.GXORGANIZATIONKEY, map);
							jedisService.save(JedisKey.GXORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944659")) { // 1944659
																		// 贵州
							jedisService.save(JedisKey.GZORGANIZATIONKEY, map);
							jedisService.save(JedisKey.GZORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944651")) { // 1944651
																		// 黑龙江
							jedisService.save(JedisKey.HLJORGANIZATIONKEY, map);
							jedisService.save(JedisKey.HLJORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944669")) { // 1944669
																		// 河北
							jedisService.save(JedisKey.HBORGANIZATIONKEY, map);
							jedisService.save(JedisKey.HBORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944653")) { // 1944653
																		// 湖北
							jedisService.save(JedisKey.HEORGANIZATIONKEY, map);
							jedisService.save(JedisKey.HEORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944667")) { // 1944667
																		// 河南
							jedisService.save(JedisKey.HNORGANIZATIONKEY, map);
							jedisService.save(JedisKey.HNORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944660")) { // 1944660
																		// 海南
							jedisService.save(JedisKey.HAORGANIZATIONKEY, map);
							jedisService.save(JedisKey.HAORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944655")) { // 1944655
																		// 湖南
							jedisService.save(JedisKey.HNAORGANIZATIONKEY, map);
							jedisService.save(JedisKey.HNAORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944650")) { // 1944650
																		// 江西
							jedisService.save(JedisKey.JXORGANIZATIONKEY, map);
							jedisService.save(JedisKey.JXORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944658")) { // 1944658
																		// 吉林
							jedisService.save(JedisKey.JLORGANIZATIONKEY, map);
							jedisService.save(JedisKey.JLORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944643")) { // 1944643
																		// 江苏
							jedisService.save(JedisKey.JSORGANIZATIONKEY, map);
							jedisService.save(JedisKey.JSORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944668")) { // 1944668
																		// 辽宁
							jedisService.save(JedisKey.LNORGANIZATIONKEY, map);
							jedisService.save(JedisKey.LNORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944661")) { // 1944661
																		// 宁夏
							jedisService.save(JedisKey.NXORGANIZATIONKEY, map);
							jedisService.save(JedisKey.NXORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944671")) { // 1944671
																		// 内蒙古
							jedisService.save(JedisKey.NMGORGANIZATIONKEY, map);
							jedisService.save(JedisKey.NMGORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944662")) { // 1944662
																		// 青海
							jedisService.save(JedisKey.QHORGANIZATIONKEY, map);
							jedisService.save(JedisKey.QHORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944642")) { // 1944642
																		// 上海
							jedisService.save(JedisKey.SHORGANIZATIONKEY, map);
							jedisService.save(JedisKey.SHORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944670")) { // 1944670
																		// 山西
							jedisService.save(JedisKey.SXORGANIZATIONKEY, map);
							jedisService.save(JedisKey.SXORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944666")) { // 1944666
																		// 山东
							jedisService.save(JedisKey.SDORGANIZATIONKEY, map);
							jedisService.save(JedisKey.SDORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944656")) { // 1944656
																		// 陕西
							jedisService.save(JedisKey.SIORGANIZATIONKEY, map);
							jedisService.save(JedisKey.SIORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944654")) { // 1944654
																		// 四川
							jedisService.save(JedisKey.SCORGANIZATIONKEY, map);
							jedisService.save(JedisKey.SCORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944665")) { // 1944665
																		// 天津
							jedisService.save(JedisKey.TJORGANIZATIONKEY, map);
							jedisService.save(JedisKey.TJORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944648")) { // 1944648
																		// 新疆
							jedisService.save(JedisKey.XJORGANIZATIONKEY, map);
							jedisService.save(JedisKey.XJORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944663")) { // 1944663
																		// 西藏
							jedisService.save(JedisKey.XZORGANIZATIONKEY, map);
							jedisService.save(JedisKey.XZORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944657")) { // 1944657
																		// 云南
							jedisService.save(JedisKey.YNORGANIZATIONKEY, map);
							jedisService.save(JedisKey.YNORGANIZATIONKEY_DATE, sdf.format(d));
						} else if (organizationID.equals("1944644")) { // 1944644
																		// 浙江
							jedisService.save(JedisKey.ZJORGANIZATIONKEY, map);
							jedisService.save(JedisKey.ZJORGANIZATIONKEY_DATE, sdf.format(d));
						}
						Collections.sort(organizationList);
						organizationMap.put("organizationDate", sdf.format(d));
						organizationMap.put("organizationList", organizationList);
					}
				} else {// 存在 则为首次同步数据 返回全部
						// 最上级
					if (organizationID.equals("0")) {
						organizationMap.put("name", "up");
						jedisTime = jedisService.getValue(JedisKey.UPORGANIZATIONKEY_DATE);
						redisOrganization = jedisService.values(JedisKey.UPORGANIZATIONKEY);
						List<OrganizationBean> list = new ArrayList<OrganizationBean>();
						OrganizationBean ooo = new OrganizationBean();
						for (Object re : redisOrganization) {
							OrganizationBean organization = JsonUtil.fromJson(re.toString(), ooo.getClass());
							list.add(organization);
						}
						Collections.sort(list);
						organizationMap.put("organizationList", list);
						organizationMap.put("organizationDate", jedisTime);
					} else {
						if (organizationID.equals("1844641")) { // 1844641 集团总部
							organizationMap.put("name", "jt");
							redisOrganization = jedisService.values(JedisKey.JTORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.JTORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1844643")) { // 1844643
																		// 专业公司及运营单位
							organizationMap.put("name", "zy");
							redisOrganization = jedisService.values(JedisKey.ZYORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.ZYORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944646")) { // 1944646
																		// 安徽
							organizationMap.put("name", "ah");
							redisOrganization = jedisService.values(JedisKey.AHORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.AHORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944664")) { // 1944664
																		// 北京
							organizationMap.put("name", "bj");
							redisOrganization = jedisService.values(JedisKey.BJORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.BJORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944649")) { // 1944649
																		// 重庆
							organizationMap.put("name", "cq");
							redisOrganization = jedisService.values(JedisKey.CQORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.CQORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944645")) { // 1944645
																		// 福建
							organizationMap.put("name", "fj");
							redisOrganization = jedisService.values(JedisKey.FJORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.FJORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944641")) { // 1944641
																		// 广东
							organizationMap.put("name", "gd");
							redisOrganization = jedisService.values(JedisKey.GDORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.GDORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944652")) { // 1944652
																		// 甘肃
							organizationMap.put("name", "gs");
							redisOrganization = jedisService.values(JedisKey.GSORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.GSORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944647")) { // 1944647
																		// 广西
							organizationMap.put("name", "gx");
							redisOrganization = jedisService.values(JedisKey.GXORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.GXORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944659")) { // 1944659
																		// 贵州
							organizationMap.put("name", "gz");
							redisOrganization = jedisService.values(JedisKey.GZORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.GZORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944651")) { // 1944651
																		// 黑龙江
							organizationMap.put("name", "hlj");
							redisOrganization = jedisService.values(JedisKey.HLJORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.HLJORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944669")) { // 1944669
																		// 河北
							organizationMap.put("name", "hb");
							redisOrganization = jedisService.values(JedisKey.HBORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.HBORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944653")) { // 1944653
																		// 湖北
							organizationMap.put("name", "he");
							redisOrganization = jedisService.values(JedisKey.HEORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.HEORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944667")) { // 1944667
																		// 河南
							organizationMap.put("name", "hn");
							redisOrganization = jedisService.values(JedisKey.HNORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.HNORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944660")) { // 1944660
																		// 海南
							organizationMap.put("name", "ha");
							redisOrganization = jedisService.values(JedisKey.HAORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.HAORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944655")) { // 1944655
																		// 湖南
							organizationMap.put("name", "hna");
							redisOrganization = jedisService.values(JedisKey.HNAORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.HNAORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944650")) { // 1944650
																		// 江西
							organizationMap.put("name", "jx");
							redisOrganization = jedisService.values(JedisKey.JXORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.JXORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944658")) { // 1944658
																		// 吉林
							organizationMap.put("name", "jl");
							redisOrganization = jedisService.values(JedisKey.JLORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.JLORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944643")) { // 1944643
																		// 江苏
							organizationMap.put("name", "js");
							redisOrganization = jedisService.values(JedisKey.JSORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.JSORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944668")) { // 1944668
																		// 辽宁
							organizationMap.put("name", "ln");
							redisOrganization = jedisService.values(JedisKey.LNORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.LNORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944661")) { // 1944661
																		// 宁夏
							organizationMap.put("name", "nx");
							redisOrganization = jedisService.values(JedisKey.NXORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.NXORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944671")) { // 1944671
																		// 内蒙古
							organizationMap.put("name", "nmg");
							redisOrganization = jedisService.values(JedisKey.NMGORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.NMGORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944662")) { // 1944662
																		// 青海
							organizationMap.put("name", "qh");
							redisOrganization = jedisService.values(JedisKey.QHORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.QHORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944642")) { // 1944642
																		// 上海
							organizationMap.put("name", "sh");
							redisOrganization = jedisService.values(JedisKey.SHORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.SHORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944670")) { // 1944670
																		// 山西
							organizationMap.put("name", "sx");
							redisOrganization = jedisService.values(JedisKey.SXORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.SXORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944666")) { // 1944666
																		// 山东
							organizationMap.put("name", "sd");
							redisOrganization = jedisService.values(JedisKey.SDORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.SDORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944656")) { // 1944656
																		// 陕西
							organizationMap.put("name", "si");
							redisOrganization = jedisService.values(JedisKey.SIORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.SIORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944654")) { // 1944654
																		// 四川
							organizationMap.put("name", "sc");
							redisOrganization = jedisService.values(JedisKey.SCORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.SCORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944665")) { // 1944665
																		// 天津
							organizationMap.put("name", "tj");
							redisOrganization = jedisService.values(JedisKey.TJORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.TJORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944648")) { // 1944648
																		// 新疆
							organizationMap.put("name", "xj");
							redisOrganization = jedisService.values(JedisKey.XJORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.XJORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944663")) { // 1944663
																		// 西藏
							organizationMap.put("name", "xz");
							redisOrganization = jedisService.values(JedisKey.XZORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.XZORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944657")) { // 1944657
																		// 云南
							organizationMap.put("name", "yn");
							redisOrganization = jedisService.values(JedisKey.YNORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.YNORGANIZATIONKEY_DATE);
						} else if (organizationID.equals("1944644")) { // 1944644
																		// 浙江
							organizationMap.put("name", "zj");
							redisOrganization = jedisService.values(JedisKey.ZJORGANIZATIONKEY);
							jedisTime = jedisService.getValue(JedisKey.ZJORGANIZATIONKEY_DATE);
						}
						if (sdf.parse(jedisTime).getTime() - sdf.parse(orgtreeDate).getTime() <= 0) {// 时间相等，没有需要更新的数据
							List<OrganizationBean> list = new ArrayList<OrganizationBean>();
							organizationMap.put("organizationList", list);
							organizationMap.put("organizationDate", orgtreeDate);
						} else {
							List<OrganizationBean> list = new ArrayList<OrganizationBean>();
							OrganizationBean ooo = new OrganizationBean();
							for (Object re : redisOrganization) {
								OrganizationBean organization = JsonUtil.fromJson(re.toString(), ooo.getClass());
								list.add(organization);
							}
							Collections.sort(list);
							organizationMap.put("organizationList", list);
							organizationMap.put("organizationDate", jedisTime);
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return organizationMap;
	}

	/**
	 * 查询省份和领域
	 * 
	 * @time 2017-08-24
	 * @author hzh
	 */
	@Override
	public Map getOrgName() {

		List<Map> list = new ArrayList<Map>();
		List<Map> list2 = new ArrayList<Map>();
		Map<String, List> map = new HashMap<String, List>();

		/**
		 * 查询省份
		 */
		List<OrgTer> orgList = organizationDao.selectOrgName();

		for (OrgTer org : orgList) {
			Map<String, String> ma = new LinkedHashMap<String, String>();
			ma.put("organizationName", org.getOrganizationName());
			ma.put("organizationId", org.getOrganizationId());
			list.add(ma);
		}

		map.put("organizationNameList", list);
		/**
		 * 查询领域
		 */
		List<OrgTer> ter = organizationDao.selectTerName();

		for (OrgTer oter : ter) {
			Map<String, String> mas = new LinkedHashMap<String, String>();
			mas.put("terName", oter.getTerName());
			mas.put("terId", oter.getTerId());
			list2.add(mas);
		}
		map.put("terNameList", list2);
		return map;
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param organid
	 * @return
	 */
	public OrganizationBean getOrganizationByOrganid(String organid) {
		return organizationDao.getOrganizationByOrganid(organid);
	}

	/**
	 * 获取50地市组织
	 * 
	 * @param loginID
	 * @return
	 */
	public Map<String, Object> fiveCityOrganization(String loginID) {
		Map<String, Object> organizationMap = new HashMap<String, Object>();
		// 从redis中取出全部数据

		//JedisPool jedisPool = new JedisPool("","",0,"");

		//Jedis jedis = new Jedis(redis_host, redis_port,redis_timeout);
		//List<Object> redisOrganization = jedis..get(JedisKey.FIVEORGANIZATIONKEY);
		/*JedisPool jedisPool = new JedisPool(redis_host,redis_port,"",redis_timeout);*/
		List<Object> redisOrganization = jedisService.values(JedisKey.FIVEORGANIZATIONKEY);

		// 缓存中不存在
		if (redisOrganization == null || redisOrganization.size() == 0) {
			List<OrganizationBean> organizationList = organizationDao.fiveCityOrganization(loginID);
			Map<String, String> map = new LinkedHashMap<String, String>();
			for (OrganizationBean o : organizationList) {
				try {
					map.put(o.getOrganizationID(), JsonUtil.toJson(o));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
			if (map.size() > 0) {
				jedisService.save(JedisKey.FIVEORGANIZATIONKEY, map);
				Collections.sort(organizationList);
				organizationMap.put("organizationList", organizationList);
			}
		} else {// 存在 则为首次同步数据 返回全部
			List<OrganizationBean> list = new ArrayList<OrganizationBean>();
			OrganizationBean ooo = new OrganizationBean();
			for (Object re : redisOrganization) {
				OrganizationBean organization = JsonUtil.fromJson(re.toString(), ooo.getClass());
				list.add(organization);
			}
			Collections.sort(list);
			organizationMap.put("organizationList", list);
		}
		return organizationMap;
	}
	/**
	 * 积分
	 * @param map
	 * @return
	 */
	@Override
	public int getCountIntegralByDay(Map<String, String> map) {
		return organizationDao.getCountIntegralByDay(map);
	}
}
