package com.example.camel.camel.transacted.processor;

import com.example.camel.camel.transacted.exception.RetriableException;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class ExceptionElevationAndRemovalProcessor {

    public String observe(Exchange exchange) throws Exception {

        Map<String, Object> properties = exchange.getProperties();
        Exception exception = (Exception)properties.get(Exchange.EXCEPTION_CAUGHT);
        Boolean failureHandled = (Boolean) properties.get(Exchange.FAILURE_HANDLED);

        properties.remove(Exchange.FAILURE_ENDPOINT);
        properties.remove(Exchange.FAILURE_ROUTE_ID);
        properties.remove(Exchange.FATAL_FALLBACK_ERROR_HANDLER);
        properties.remove(Exchange.FAILURE_HANDLED);
        properties.remove(Exchange.EXCEPTION_CAUGHT);


        if(exception != null && failureHandled != null){
            throw exception;
        }

//        if(true){
//            throw new RuntimeException();
//        }
//        if(exception != null)
//            throw exception;


//        System.out.println("EXCHANGE OBSERVER CALLED");

        return "OUT";
    }
}
