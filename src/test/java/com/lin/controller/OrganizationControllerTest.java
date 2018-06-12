package com.lin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.domain.OrganizationDsl;
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
    mockMvc.perform(MockMvcRequestBuilders.post("/communicationdsl/organizationlist")
        .accept(MediaType.APPLICATION_FORM_URLENCODED).content("pid=0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(true))
        .andDo(print());
/*
    u.setId(2);
    u.setAccount("yaloo1");
    u.setCreateTime(new Date());
    u.setEnable(true);


    mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(u)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(true))
        .andDo(print());


    u.setId(3);
    u.setAccount("yaloo3");
    u.setCreateTime(new Date());


    mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(u)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(true))
        .andDo(print());*/
  }


}
