package com.lin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.mapper.DepartmentMapper;
import com.lin.domain.Department;
import com.lin.service.DepartmentServiceI;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Service("departmentService")
public class DepartmentServiceImpl implements  DepartmentServiceI{

	@Autowired
	private DepartmentMapper departmentDao;

	/**
	 * 查询所有部门
	 */
	@Override
	public List<Department> selectAllDepartment(String department) {
		List<Department> list=departmentDao.selectAllDepartment(department);
		return list;
	}
}
