package com.lin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.mapper.PostMapper;
import com.lin.domain.Position;
import com.lin.service.PostServiceI;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Service("postService")
public class PostServiceImpl implements PostServiceI {

	@Autowired
	private PostMapper postDao;

	@Override
	public List<Position> selectAllPosition() {
		List<Position> list = postDao.selectAllPosition();
		return list;
	}
	@Override
	public String getPositionNameByID(String posID){
		return postDao.getPositionNameByID(posID);
	}
}
