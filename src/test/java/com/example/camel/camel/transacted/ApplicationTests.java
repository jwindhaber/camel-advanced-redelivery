package com.example.camel.camel.transacted;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.example.camel.camel.transacted.route.EntryPointRoute.ENTRY_POINT_ROUTE;
import static com.example.camel.camel.transacted.route.ServiceRouteOuter.SERVICE_ROUTE_OUTER;
import static org.apache.camel.ExchangePattern.InOut;

@SpringBootTest
class ApplicationTests {

	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
		Integer numberOfApplesBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM APPLE", Integer.class);
		System.out.println(numberOfApplesBefore);
		String some_response = (String)producerTemplate.sendBody(ENTRY_POINT_ROUTE, InOut, "SOME_BODY");

		System.out.println(some_response);

		Integer numberOfApples = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM APPLE", Integer.class);
		System.out.println(numberOfApples);


	}

}
