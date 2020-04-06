package com.example.camel.camel.transacted.service;

import org.apache.camel.Body;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Service
public class AppleProducer {

    JdbcTemplate jdbcTemplate;

    public AppleProducer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String produceApples(@Body String someMessage){
        System.out.println("SOME APPLES CALLED!");
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("APPLE");
        simpleJdbcInsert.execute(new MapSqlParameterSource("apple_id", UUID.randomUUID().toString()));
        return someMessage;
    }

}
