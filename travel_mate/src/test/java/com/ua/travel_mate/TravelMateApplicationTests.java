package com.ua.travel_mate;

import com.ua.travel_mate.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TravelMateApplicationTests {

	@Autowired
	private UserController controller;

	@Test
	void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}

}
