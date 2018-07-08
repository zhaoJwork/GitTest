package com.lin.service;

import java.util.List;

import com.lin.domain.Department;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
public interface DepartmentServiceI {
	
	List<Department> selectAllDepartment(String department);
}
