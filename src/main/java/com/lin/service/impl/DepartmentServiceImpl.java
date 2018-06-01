package com.lin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.dao.DepartmentDaoI;
import com.lin.domain.Department;
import com.lin.domain.Group;
import com.lin.service.DepartmentServiceI;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Service("departmentService")
public class DepartmentServiceImpl implements  DepartmentServiceI{

	@Autowired
	private DepartmentDaoI departmentDao;

	/**
	 * 查询所有部门
	 */
	@Override
	public List<Department> selectAllDepartment(String department) {
		List<Department> list=departmentDao.selectAllDepartment(department);
		return list;
	}
}
