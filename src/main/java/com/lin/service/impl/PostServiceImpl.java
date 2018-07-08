package com.lin.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.domain.Position;
import com.lin.domain.QPositionDsl;
import com.lin.mapper.PostMapper;
import com.lin.service.PostServiceI;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Service("postService")
public class PostServiceImpl implements PostServiceI {

	@Autowired
	private PostMapper postDao;
	
	// sql 修改为jpa+querydsl 
	
	@Autowired
    private EntityManager entityManager;
	
	private JPAQueryFactory queryFactory; 
	
	@PostConstruct  
    public void init() {  
       queryFactory = new JPAQueryFactory(entityManager);  
    }

	@Override
	public List<Position> selectAllPosition() {
		QPositionDsl qPositionDsl = QPositionDsl.positionDsl;
		return queryFactory.select(
				Projections.bean(
						Position.class, 
						qPositionDsl.posId.as("postID"),
						qPositionDsl.posName.as("postName")
						)
				)
				.from(qPositionDsl).fetch();
	}
	@Override
	public String getPositionNameByID(String posID){
//		select p.pos_name from appuser.address_position p where
//		p.pos_id = #{_parameter}
		
		
		return postDao.getPositionNameByID(posID);
	}
}
