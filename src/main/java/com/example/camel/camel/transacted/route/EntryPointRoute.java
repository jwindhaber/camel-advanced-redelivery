package com.example.camel.camel.transacted.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Service;


@Service
public class EntryPointRoute  extends RouteBuilder {

    public static final String ENTRY_POINT_ROUTE = "seda:ENTRY-POINT-OUTER";

    @Override
    public void configure() {

        onException(Exception.class).maximumRedeliveries(3);
        onException(RuntimeException.class).maximumRedeliveries(3);

        from(ENTRY_POINT_ROUTE)
                .errorHandler(defaultErrorHandler())
                .log("START OF ENTRYPOINT")
                .to(TransactionalServiceRouteWrapper.SERVICE_ROUTE_OUTER)
                .log("END OF ENTRYPOINT");
    }
}
