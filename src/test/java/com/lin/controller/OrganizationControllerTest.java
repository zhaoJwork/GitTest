package com.lin.controller;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganizationControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void A00_get() throws Exception {
      String responseString = mockMvc.perform(
              MockMvcRequestBuilders.get("/organ/getDepList")    //请求的url,请求的方法是get
                      .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
                      .param("loginID","1")
                      .param("provinceID","1")//添加参数
      ).andExpect(status().isOk())    //返回的状态是200
              .andDo(print())         //打印出请求和相应的内容
              .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
      System.out.println("--------返回的json = " + responseString);

  }


}
