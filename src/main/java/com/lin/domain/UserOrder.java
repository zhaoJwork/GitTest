package com.lin.domain;
/**
 * 用户排序实体类
 * @author machao
 *
 */
public class UserOrder {
	//用户id
	private String user_id;
	//用户部门
	private String dep_id;
	//用户排序序号
	private int new_order_num;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getDep_id() {
		return dep_id;
	}
	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}
	public int getNew_order_num() {
		return new_order_num;
	}
	public void setNew_order_num(int new_order_num) {
		this.new_order_num = new_order_num;
	}
	
	
	
}
