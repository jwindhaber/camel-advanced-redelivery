package com.example.camel.camel.transacted.config;

import com.example.camel.camel.transacted.definition.TransactionSpecification;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author juergen.windhaber
 * @since 17.4.0
 */
@Configuration
public class CamelConfiguration {

    @Bean(name = TransactionSpecification.REQUIRES_NEW)
    public SpringTransactionPolicy transactionRequiresNewPolicy(PlatformTransactionManager transactionManager) {
        SpringTransactionPolicy transactionPolicy = new SpringTransactionPolicy(transactionManager);

        transactionPolicy.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
        return transactionPolicy;
    }

}
