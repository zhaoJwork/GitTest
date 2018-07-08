package com.lin.mapper;

import com.lin.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 * @author lwz
 * @date 2017年11月12日
 */
@Mapper
public interface SourceMapper {
	//编辑用户
	void editUser(User u);
	//增加黑名单
	void addBlack(User u);
	//删除黑名单
	void delBlack(User u);
	//存储过程刷新人员数量
	void pro_appuser(User u);
	
}
