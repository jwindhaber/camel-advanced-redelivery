package com.example.camel.camel.transacted.service;

import com.example.camel.camel.transacted.exception.RetriableException;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class StrawberryProducer {

    public static Integer counter = 0;

    public String producePears(@Body String someBody, Exchange exchange){

        Exception exception = (Exception)exchange.getProperties().get(Exchange.EXCEPTION_CAUGHT);

        if(counter < 3){
            counter++;
//            throw new RetriableException("SOME RETRY EXCEPTION");
//            throw new RuntimeException("SOME RETRY EXCEPTION");
        }
        return someBody;
    }

}
