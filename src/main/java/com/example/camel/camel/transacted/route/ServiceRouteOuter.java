package com.example.camel.camel.transacted.route;

import com.example.camel.camel.transacted.service.ExchangeObserverAfter;
import com.example.camel.camel.transacted.service.ExchangeObserverBefore;
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
public class ServiceRouteOuter extends SpringRouteBuilder {


    public static final String SERVICE_ROUTE_OUTER = "direct:Service-Route-Outer";

    @Autowired
    @Qualifier(REQUIRES_NEW)
    SpringTransactionPolicy requiresNew;

    @Override
    public void configure() throws Exception {

        from(SERVICE_ROUTE_OUTER)
                .errorHandler(noErrorHandler())
                .log("START OF OUTER SERVICE")
                .bean(ExchangeObserverBefore.class)
                .to(TransactionalServiceRouteWrapper.TRANSACTIONAL_SERVICE_ROUTE_WRAPPER)
                .bean(ExchangeObserverAfter.class)
                .log("END OF OUTER SERVICE");

    }
}
