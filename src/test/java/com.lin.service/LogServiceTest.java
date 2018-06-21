package com.lin.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lin.domain.AddressInfLog;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogServiceTest {

  @Autowired
  private AddressInfLogServiceImpl logService;
  @Test
  public void testLoad(){
    AddressInfLog log =  logService.getInfLog(null,"测试");
    logService.saveAddressInfLog(log,null);
  }
}
