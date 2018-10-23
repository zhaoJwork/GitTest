package com.lin.service;

import com.lin.domain.OrganizationDsl;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationServiceTest {

  @Autowired
  private OrganizationService organizationServiceDsl;

  /**
   * 测试按上一级查询组织部门
   */
  @Test
  public void testLoad(){
    OrganizationDsl dsl = new OrganizationDsl();
    dsl.setpID("0");
    List<OrganizationDsl> list = organizationServiceDsl.getOrganizationByDsl(dsl);
    Assert.assertNotNull(list);
  }

}
