package com.lin.service;

import com.lin.domain.AddressInfLogDsl;
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
public class LogServiceTest {

  @Autowired
  private AddressInfLogServiceDslImpl logServiceDsl;
  @Test
  public void testLoad(){
    AddressInfLogDsl log =  logServiceDsl.getInfLog(null,"测试");
    logServiceDsl.saveAddressInfLog(log,null);
  }
}
