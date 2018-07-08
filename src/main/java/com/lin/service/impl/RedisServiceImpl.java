package com.lin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lin.service.RedisServiceI;

/**
 * redis
 * 
 * @author zhangWeiJie
 * @date 2017年8月22日
 */
@Service("jedisService")
public class RedisServiceImpl implements RedisServiceI{

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public void save(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void save(String key, Map<String, String> value) {
		redisTemplate.opsForHash().putAll(key, value);
	}

	@Override
	public void save(String key, String hashKey, String value) {
		redisTemplate.opsForHash().put(key,hashKey,value);
	}

	@Override
	public String getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public List<Object> values(String key) {
		return redisTemplate.opsForHash().values(key);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}  
}
