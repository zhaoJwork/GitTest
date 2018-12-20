package com.lin.service;

import com.lin.domain.User;
import java.util.List;
import java.util.Map;

import com.lin.vo.OutParentUser;
import com.lin.vo.OutUser;
import com.lin.vo.UserDetailsVo;
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

  /**
   * 分页获取用户列表
   */
  @Test
  public void getUserList(){
    OutParentUser users = userService.getUserList("10","1",
            "","","","","1959935","","");

    Assert.assertNotNull(users);
  }
  @Test
  public void testByUserID(){
    UserDetailsVo users = userService.selectUserDetails("113420","101539");
    Assert.assertNotNull(users);
  }
}
