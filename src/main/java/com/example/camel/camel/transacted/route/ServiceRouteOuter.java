package com.example.camel.camel.transacted.route;

import com.example.camel.camel.transacted.processor.ExceptionElevationAndRemovalProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.example.camel.camel.transacted.definition.TransactionSpecification.REQUIRES_NEW;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class ServiceRouteOuter extends RouteBuilder {


    public static final String SERVICE_ROUTE_OUTER = "direct:SERVICE-ROUTE-OUTER";

    @Autowired
    @Qualifier(REQUIRES_NEW)
    SpringTransactionPolicy requiresNew;

    @Override
    public void configure() {

        from(SERVICE_ROUTE_OUTER)
                .errorHandler(noErrorHandler())
                .log("START OF OUTER SERVICE")
                .to(TransactionalServiceRouteWrapper.TRANSACTIONAL_SERVICE_ROUTE_WRAPPER)
                .bean(ExceptionElevationAndRemovalProcessor.class)
                .log("END OF OUTER SERVICE");

    }
}
