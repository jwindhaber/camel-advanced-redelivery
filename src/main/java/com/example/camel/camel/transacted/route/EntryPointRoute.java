package com.example.camel.camel.transacted.route;

import com.example.camel.camel.transacted.service.ExchangeObserverAfter;
import com.example.camel.camel.transacted.service.ExchangeObserverBefore;
import com.example.camel.camel.transacted.service.ExchangeObserverEntryPointReturn;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Service;

import static com.example.camel.camel.transacted.route.ServiceRouteOuter.SERVICE_ROUTE_OUTER;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class EntryPointRoute  extends SpringRouteBuilder {

    public static final String ENTRY_POINT_ROUTE = "seda:Entry-Point-Outer";

    @Override
    public void configure() throws Exception {

        onException(Exception.class).maximumRedeliveries(3);
        onException(RuntimeException.class).maximumRedeliveries(3);

        from(ENTRY_POINT_ROUTE)
                .errorHandler(defaultErrorHandler())
                .log("START OF ENTRYPOINT")
                .to(SERVICE_ROUTE_OUTER)
                .bean(ExchangeObserverEntryPointReturn.class)
                .log("END OF ENTRYPOINT");
    }
}