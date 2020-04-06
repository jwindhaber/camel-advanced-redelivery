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
public class PearsProducer {

    JdbcTemplate jdbcTemplate;

    public PearsProducer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String producePears(@Body String someMessage){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("PEAR");
        simpleJdbcInsert.execute(new MapSqlParameterSource("pear_id", UUID.randomUUID().toString()));
        return someMessage;
    }

}
