package com.tongjx.configserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConfigserverApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testConfigEmpty() throws Exception {
		mockMvc.perform(get("/ppp/default"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name").value("ppp"))
			.andExpect(jsonPath("$.propertySources").isEmpty());
	}
	
	@Test
	public void testConfigDefault() throws Exception {
		mockMvc.perform(get("/ops2service/default"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.name").value("ops2service"))
			.andExpect(jsonPath("$.propertySources.length()").value(1))
			.andExpect(jsonPath("$.propertySources[0].name").value("classpath:testconfig/ops2service.yml"));
	}
	
	@Test
	public void testConfigDev() throws Exception {
		mockMvc.perform(get("/ops2service/dev"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
			.andExpect(jsonPath("$.name").value("ops2service"))
			.andExpect(jsonPath("$.propertySources.length()").value(2))
			.andExpect(jsonPath("$.propertySources[0].name").value("classpath:testconfig/ops2service-dev.yml"))
			.andExpect(jsonPath("$.propertySources[1].name").value("classpath:testconfig/ops2service.yml"));
	}

}
