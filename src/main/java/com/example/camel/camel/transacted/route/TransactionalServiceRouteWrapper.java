package com.example.camel.camel.transacted.route;

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

    public static final String TRANSACTIONAL_SERVICE_ROUTE_WRAPPER = "direct:TRANSACTIONAL_SERVICE_ROUTE_WRAPPER";

    @Override
    public void configure() throws Exception {

        from(TRANSACTIONAL_SERVICE_ROUTE_WRAPPER)
                .errorHandler(transactionErrorHandler(requiresNew))
                .onException(Exception.class).markRollbackOnlyLast().end()
                .policy(requiresNew)
                .log("START OF SERVICE WRAPPER")
                .to(INNER_SERVICE_ROUTE)
                .log("END OF SERVICE WRAPPER");
    }
}
