package com.lin.service;

import java.util.List;
import java.util.Map;

/**
 * redis 服务类
 * @author zhangWeiJie
 * @date 2017年8月22日
 */
public interface RedisServiceI {

	/**
	 * @param key
	 * @param value
	 * @author zhangWeiJie
	 * @date 2017年8月22日
	 * @describe 保存单个String
	 */
	void save(String key,String value);
	/**
	 * @param key
	 * @param value 
	 * @return void
	 * @author zhangWeiJie
	 * @date 2017年8月22日
	 * @describe 保存Map
	 */
	void save(String key,Map<String, String> value);
	/**
	 * @param key
	 * @param value 
	 * @return void
	 * @author zhangWeiJie
	 * @date 2017年8月22日
	 * @describe 保存或更新map中的一个对象
	 */
	void save(String key, String hashKey, String value);
	/**
	 * @param key
	 * @param value
	 * @author zhangWeiJie
	 * @date 2017年8月22日
	 * @describe 获取一个String对象
	 */
	String getValue(String key);
	/**
	 * @param key
	 * @param value
	 * @author zhangWeiJie
	 * @date 2017年8月22日
	 * @describe 获取Map的entity集合
	 */
	List<Object> values(String key);
	/**
	 * @param key
	 * @param value
	 * @author zhangWeiJie
	 * @date 2017年8月22日
	 * @describe 删除一个对象
	 */
	void delete(String key);
}
