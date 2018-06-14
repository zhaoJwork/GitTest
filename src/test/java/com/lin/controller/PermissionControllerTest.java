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

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionControllerTest {

	private MockMvc mockMvc;

	  @Autowired
	  private WebApplicationContext wac;

	  @Before
	  public void setup() {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	  }
	  
	  @Test
	  public void A00_getBannedSayCheck()  throws Exception {
		 /* mockMvc.perform(MockMvcRequestBuilders.get("/permission/bannedsaycheck")
				  .contentType(MediaType.APPLICATION_JSON)
				  .contentType(contentType)
				  )*/
	  }
	
}
