package com.lin.dao;

import java.util.List;

import com.lin.domain.Position;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface PostDaoI {
	List<Position> selectAllPosition();
	String getPositionNameByID(String posID);
}
