package com.lin.service;

/**
 * TODO
 * @author lwz
 * @date 2017年11月12日
 */
public interface SourceServiceI {
	//添加黑名单
	void addBlackUser(String context);
	//移除黑名单
	void delBlackUser(String context);
	//刷新人员数量
	void refurbishUserCount();
}
