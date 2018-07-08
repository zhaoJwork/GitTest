package com.lin.mapper;

import org.apache.ibatis.annotations.Mapper;
/**
 * 公共方法
 * @author lwz
 *
 */
@Mapper
public interface UtilMapper {
	
	/**
	 * 查询序列 seq_app_addresslist
	 * @return
	 */
	String getSeqAppAddresslist();
	
	/**
	 * 查询组与人员关联表序列
	 * @return
	 */
	String getSeqAppGourpUser();
	
	/**
	 * 擅长领域与人员关联表序列
	 * @return
	 */
	String getSeqAppUserTerritory();
	
	/**
	 * 工作内容与人员关联表序列
	 * @return
	 */
	String getSeqAppUserWork();
	
	/**
	 * 日志序列
	 * @return
	 */
	String getSeqAppAddressInfLog();
}
