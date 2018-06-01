package com.lin.service;

import java.util.List;

import com.lin.domain.Position;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface PostServiceI {
	
	List<Position> selectAllPosition();
	
	String getPositionNameByID(String posID);
}
