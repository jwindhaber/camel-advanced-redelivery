package com.example.camel.camel.transacted.route;

import com.example.camel.camel.transacted.exception.RetriableException;
import com.example.camel.camel.transacted.route.error.ErrorRoute;
import com.example.camel.camel.transacted.service.ExceptionProducer;
import com.example.camel.camel.transacted.service.NoopProducer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Service;

import static com.example.camel.camel.transacted.route.error.ErrorRoute.*;


@Service
public class EntryPointRoute extends RouteBuilder {

    public static final String ENTRY_POINT_ROUTE = "seda:ENTRY-POINT-OUTER";

    @Override
    public void configure() {

        onException(RuntimeException.class).maximumRedeliveries(0).handled(false);
        onException(RetriableException.class).maximumRedeliveries(3).handled(false);

        from(ENTRY_POINT_ROUTE).routeId("ENTRY_POINT_ROUTE")
                .log("START OF ENTRYPOINT")
                .to(TransactionalServiceRouteWrapper.SERVICE_ROUTE_OUTER)
                .log("END OF ENTRYPOINT");
    }
}
