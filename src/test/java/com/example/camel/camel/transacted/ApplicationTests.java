package com.example.camel.camel.transacted;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.example.camel.camel.transacted.route.EntryPointRoute.ENTRY_POINT_ROUTE;
import static org.apache.camel.ExchangePattern.InOut;

@SpringBootTest
class ApplicationTests {

	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void theApplesAreInserted3TimesButOnlyOneResultMustBePersisted() {
		Integer numberOfApplesBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM APPLE", Integer.class);
		Assertions.assertEquals(0, numberOfApplesBefore);

		System.out.println(numberOfApplesBefore);
		try {
			String some_response = (String)producerTemplate.sendBody(ENTRY_POINT_ROUTE, InOut, "SOME_BODY");
			System.out.println(some_response);

		} catch (Exception exception){
			// noop
		}

		Integer numberOfApples = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM APPLE", Integer.class);
		System.out.println(numberOfApples);
		Assertions.assertEquals(1, numberOfApples);


	}

}
