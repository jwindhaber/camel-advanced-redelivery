package com.example.camel.camel.transacted.route;

import com.example.camel.camel.transacted.service.ExceptionProducer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.boot.SpringBootRoutesCollector;
import org.springframework.stereotype.Service;

import static com.example.camel.camel.transacted.route.error.ErrorRoute.ERROR_ROUTE;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class TestRoute extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

//        errorHandler(deadLetterChannel(ERROR_ROUTE)
//                .maximumRedeliveries(0)
//                .redeliveryDelay(100)
//                .retryAttemptedLogLevel(LoggingLevel.WARN)
//                .log("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ"));

        from("direct:A").routeId("A")
                .errorHandler(deadLetterChannel("direct:error"))
                .bean(ExceptionProducer.class)
                .to("direct:B");

        from("direct:B").routeId("B")
                .log("RRRRRRAAAAAAARRRR");

    }
}
