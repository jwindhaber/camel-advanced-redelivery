package com.example.camel.camel.transacted.service;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class ExchangeObserverBefore {

    public String observe(Exchange exchange) throws Exception {

        Map<String, Object> properties = exchange.getProperties();
        Exception exception = (Exception)properties.get(Exchange.EXCEPTION_CAUGHT);

//        throw new RuntimeException();
//        if(exception != null)
//            throw exception;


//        System.out.println("EXCHANGE OBSERVER CALLED");
        return "OUT";
    }
}
