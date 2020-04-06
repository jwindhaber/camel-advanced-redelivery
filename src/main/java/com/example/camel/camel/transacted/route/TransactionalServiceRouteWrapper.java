package com.example.camel.camel.transacted.route;

import com.example.camel.camel.transacted.processor.ExceptionElevationAndRemovalProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.example.camel.camel.transacted.definition.TransactionSpecification.REQUIRES_NEW;
import static com.example.camel.camel.transacted.route.InnerServiceRoute.INNER_SERVICE_ROUTE;
import static org.apache.camel.builder.Builder.bean;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class TransactionalServiceRouteWrapper extends SpringRouteBuilder {

    @Autowired
    CamelContext camelContext;

    @Autowired
    @Qualifier(REQUIRES_NEW)
    SpringTransactionPolicy requiresNew;

    public static final String TRANSACTIONAL_SERVICE_ROUTE_WRAPPER = "direct:TRANSACTIONAL-SERVICE-ROUTE-WRAPPER";
    public static final String SERVICE_ROUTE_OUTER = "direct:SERVICE-ROUTE-OUTER";

    @Override
    public void configure() throws Exception {

        from(SERVICE_ROUTE_OUTER)
                .errorHandler(noErrorHandler())
                .log("START OF OUTER SERVICE")
                .to(TRANSACTIONAL_SERVICE_ROUTE_WRAPPER)
                .bean(ExceptionElevationAndRemovalProcessor.class)
                .log("END OF OUTER SERVICE");

        from(TRANSACTIONAL_SERVICE_ROUTE_WRAPPER)
                .errorHandler(transactionErrorHandler(requiresNew))
                .onException(Exception.class).markRollbackOnlyLast().end()
                .policy(requiresNew)
                .log("START OF SERVICE WRAPPER")
                .to(INNER_SERVICE_ROUTE)
                .log("END OF SERVICE WRAPPER");
    }
}
