package com.lin.service;

import com.lin.domain.OrganizationDsl;
import com.lin.domain.User;
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
  private OrganizationServiceDslImpl organizationServiceDsl;

  /**
   * 测试按上一级查询组织部门
   */
  @Test
  public void testLoad(){
    OrganizationDsl dsl = new OrganizationDsl();
    dsl.setpID("0");
    List<OrganizationDsl> list = organizationServiceDsl.getOrganizationByDsl(dsl);
    for (OrganizationDsl  oDsl : list){
      System.out.println("-------------------------------------------------"+oDsl.getOrganizationName());
    }

    System.out.print("-------------------------------------------------"+list.size());
    Assert.assertNotNull(list);
  }
}
