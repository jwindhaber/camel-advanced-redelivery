package com.example.camel.camel.transacted.service;

import org.springframework.stereotype.Service;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class ExceptionProducer {

    public void produceException(){
        throw new RuntimeException("EXCEPTION PRODUCER");
    }

}
