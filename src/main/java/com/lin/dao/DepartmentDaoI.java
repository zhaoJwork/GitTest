package com.lin.dao;

import java.util.List;

import com.lin.domain.Department;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface DepartmentDaoI {
	
	List<Department> selectAllDepartment(String department) ;
}
