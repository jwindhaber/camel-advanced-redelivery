package com.example.camel.camel.transacted.route;

import com.example.camel.camel.transacted.service.AppleProducer;
import com.example.camel.camel.transacted.service.PearsProducer;
import com.example.camel.camel.transacted.service.StrawberryProducer;
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
public class InnerServiceRoute extends SpringRouteBuilder {

    public static final String INNER_SERVICE_ROUTE = "direct:INNER-SERVICE-ROUTE";

    @Autowired
    @Qualifier(REQUIRES_NEW)
    SpringTransactionPolicy requiresNew;


    @Override
    public void configure() throws Exception {

        from(INNER_SERVICE_ROUTE)
                .errorHandler(noErrorHandler())
                .log("START OF INNER SERVICE")
                .bean(AppleProducer.class)
                .bean(PearsProducer.class)
                .bean(StrawberryProducer.class)
                .log("END OF INNER SERVICE");
    }
}
