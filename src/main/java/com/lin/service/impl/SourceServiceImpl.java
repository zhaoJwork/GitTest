package com.lin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.mapper.SourceMapper;
import com.lin.domain.User;
import com.lin.service.SourceServiceI;

/**
 * TODO
 * @author lwz
 * @date 2017年11月12日
 */
@Service("sourceService")
public class SourceServiceImpl implements SourceServiceI {

	@Autowired
	private SourceMapper sourceDao;
	/**
	 * 添加黑名单
	 */
	public void addBlackUser(String context){
		if(null != context && !"".equals(context)){
			String[] userIDs = context.split(",");
			for (String userID : userIDs){
				User u = new User();
				u.setUserID(userID);
				u.setContext("3");
				//编辑用户
				sourceDao.editUser(u);
				//增加黑名单
				sourceDao.delBlack(u);
				//增加黑名单
				sourceDao.addBlack(u);
				
			}
		}
	}
	/**
	 * 删除黑名单
	 */
	public void delBlackUser(String context){
		if(null != context && !"".equals(context)){
			String[] userIDs = context.split(",");
			for (String userID : userIDs){
				User u = new User();
				u.setUserID(userID);
				u.setContext("2");
				//编辑用户
				sourceDao.editUser(u);
				//增加黑名单
				sourceDao.delBlack(u);
				
			}
		}
	}
	/**
	 * 刷新人员数量
	 */
	public void refurbishUserCount(){
		sourceDao.pro_appuser(new User());
	}
	

	public SourceMapper getSourceDao() {
		return sourceDao;
	}

	public void setSourceDao(SourceMapper sourceDao) {
		this.sourceDao = sourceDao;
	}
	
	
}
