package com.example.camel.camel.transacted.route.error;

import com.example.camel.camel.transacted.service.NoopProducer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;


@Service
public class ErrorRoute extends RouteBuilder {

    public static final String ERROR_ROUTE = "direct:ERROR-ROUTE";

    @Override
    public void configure() {

        from("direct:error").routeId("ERROR-ROUTE")
                .errorHandler(noErrorHandler())
                .log("Error Route called").id("ERROR-ROUTE-LOG");
//                .bean(NoopProducer.class)
//                .log("SOME ERROR ROUTE");
    }
}
