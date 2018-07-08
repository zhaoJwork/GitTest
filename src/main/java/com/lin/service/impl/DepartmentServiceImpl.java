package com.lin.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.mapper.DepartmentMapper;
import com.lin.domain.Department;
import com.lin.domain.QOrganizationDsl;
import com.lin.service.DepartmentServiceI;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * TODO
 * @author zhangWeiJie
 * @date 2017年8月18日
 */
@Service("departmentService")
public class DepartmentServiceImpl implements  DepartmentServiceI{

	@Autowired
	private DepartmentMapper departmentDao;
	
	@Autowired
    private EntityManager entityManager;
	
	private JPAQueryFactory queryFactory;  
    
    @PostConstruct  
    public void init() {  
       queryFactory = new JPAQueryFactory(entityManager);  
    }

	/**
	 * 查询所有部门
	 */
	@Override
	public List<Department> selectAllDepartment(String department) {
		
		QOrganizationDsl qOrganizationDsl = QOrganizationDsl.organizationDsl;
		
		return queryFactory.select(
				Projections.bean(
						Department.class,
						qOrganizationDsl.organizationID.as("departmentID"),
						qOrganizationDsl.organizationName.as("departmentName")
						)
				)
		.from(qOrganizationDsl)
		.where(qOrganizationDsl.pID.eq(
				JPAExpressions.select(
						qOrganizationDsl.pID
						)
				.from(qOrganizationDsl)
				.where(qOrganizationDsl.organizationID.eq(department))
				))
		.fetch();
	}
}
