package com.example.camel.camel.transacted;

import com.example.camel.camel.transacted.route.TestRoute;
import com.example.camel.camel.transacted.service.ExceptionProducer;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.reifier.RouteReifier;
import org.apache.camel.spring.ErrorHandlerDefinition;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;

import static com.example.camel.camel.transacted.route.EntryPointRoute.ENTRY_POINT_ROUTE;
import static org.apache.camel.ExchangePattern.InOut;
import static org.apache.camel.builder.AdviceWithRouteBuilder.adviceWith;

@SpringBootTest(classes = {Application.class, DummyTestsWithMocks.TestContext.class})
@CamelSpringBootTest
class DummyTestsWithMocks {


	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	CamelContext context;

//	@EndpointInject("mock:B")
//	protected MockEndpoint mockedEndpointB;

	@Test
	void testCall() throws Exception {

//		RouteReifier.adviceWith(((ModelCamelContext)context).getRouteDefinition("A"), context, new AdviceWithRouteBuilder() {
//			@Override
//			public void configure() throws Exception {
//				// when advicing then use wildcard as URI options cannot be matched
//				mockEndpointsAndSkip("direct:B*");
//			}
//		});
		MockEndpoint mockedEndpointErrorRoute = context.getEndpoint("mock:error", MockEndpoint.class);
//		MockEndpoint mockedEndpointErrorRoute = context.getEndpoint("mock:direct:ERROR-ROUTE", MockEndpoint.class);
//		MockEndpoint mockedEndpointErrorRoute = context.getEndpoint("mock:direct:error", MockEndpoint.class);

//		mockedEndpointB.expectedMessageCount(0);
		mockedEndpointErrorRoute.expectedMessageCount(1);

		producerTemplate.sendBody("direct:A", "sd");

//		mockedEndpointB.assertIsSatisfied();
		mockedEndpointErrorRoute.assertIsSatisfied();


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
//						adviceWith(camelContext, "A", advice -> advice.mockEndpointsAndSkip("direct:B*"));
//						adviceWith(camelContext, "A", advice -> advice.mockEndpointsAndSkip("direct:ERROR-ROUTE*"));
//						adviceWith(camelContext, "A", advice -> advice.mockEndpointsAndSkip("direct:error"));
//						adviceWith(camelContext, "ERROR-ROUTE", advice -> advice.mockEndpointsAndSkip("ERROR-ROUTE-LOG*"));
						adviceWith(camelContext, "ERROR-ROUTE", advice -> advice.weaveById("ERROR-ROUTE-LOG").replace().to("mock:error"));
						adviceWith(camelContext, "ERROR-ROUTE", advice -> advice.weaveById("ERROR-ROUTE-LOG").replace().to("mock:error"));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			};
		}

	}

}
