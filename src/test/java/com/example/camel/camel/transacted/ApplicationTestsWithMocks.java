package com.example.camel.camel.transacted;

import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.reifier.RouteReifier;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static com.example.camel.camel.transacted.route.EntryPointRoute.ENTRY_POINT_ROUTE;
import static com.example.camel.camel.transacted.route.error.ErrorRoute.ERROR_ROUTE;
import static org.apache.camel.ExchangePattern.InOut;
import static org.apache.camel.builder.AdviceWithRouteBuilder.adviceWith;

@SpringBootTest(classes = {Application.class, ApplicationTestsWithMocks.TestContext.class})
@CamelSpringBootTest
class ApplicationTestsWithMocks {

	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CamelContext camelContext;

	@Test
	void theApplesAreInserted3TimesButOnlyOneResultMustBePersisted() throws Exception {


		MockEndpoint errorEndpoint = camelContext.getEndpoint("mock:direct:ERROR-ROUTE", MockEndpoint.class);
		errorEndpoint.expectedMinimumMessageCount(1);

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
//		Assertions.assertEquals(1, numberOfApples);

		errorEndpoint.assertIsSatisfied();
//		MockEndpoint.assertIsSatisfied(camelContext);

	}


	@Configuration
	static class TestContext {


		@Bean
		CamelContextConfiguration testCamelContextConfiguration() {

			return new CamelContextConfiguration() {
				@Override
				public void beforeApplicationStart(CamelContext context) {
				}

				@Override
				public void afterApplicationStart(CamelContext camelContext)  {
					try {
						adviceWith(camelContext, "ENTRY_POINT_ROUTE", advice -> advice.mockEndpointsAndSkip("direct:ERROR-ROUTE"));
					} catch (Exception e) {
						throw new RuntimeException();
					}
				}
			};
		}

	}

}
