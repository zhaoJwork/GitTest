package com.lin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lin.connnector.crowd.QueryGroup;
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
public class CrowdServiceTest {

  @Autowired
  private CrowdService crowdService;

  /**
   * 查询指定人群组
   */
  @Test
  public void queryGroup(){
      QueryGroup group= crowdService.queryGroup("113420");
      Assert.assertNotNull(group);
  }

  /**
   * 根据群组ID获取群成员列表
   */
  @Test
  public void queryGroupById(){
    crowdService.queryGroupById("113420","18893");
    //Assert.assertNotNull(group);
  }

}
