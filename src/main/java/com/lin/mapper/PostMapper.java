package com.lin.mapper;

import java.util.List;

import com.lin.domain.Position;
import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Mapper
public interface PostMapper {
	List<Position> selectAllPosition();
	String getPositionNameByID(String posID);
}
