package com.lin.service;

import com.lin.domain.User;
import java.util.List;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

  @Autowired
  private UserService userService;
  @Test
  public void testLoad(){
    List<User> users = userService.findByIds("188218");
    Assert.assertNotNull(users);
  }
}
