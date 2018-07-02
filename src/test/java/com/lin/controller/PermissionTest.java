package com.lin.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lin.domain.QAddressColAuxiliary;
import com.lin.domain.QAddressCollection;
import com.lin.service.PermissionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionTest {
	
	@Autowired
	private PermissionService permissionService;

	@Test
	public void testDelete() {
		QAddressCollection qAddressCollection = QAddressCollection.addressCollection;
		QAddressColAuxiliary auxiliary = QAddressColAuxiliary.addressColAuxiliary;
		permissionService.autoDele("777;", qAddressCollection, auxiliary);
	}
	
	@Test
	public void testSele() {
		new PermissionService(null).getIsCollection("88");
	}
	
}
