package org.alexprokopiev.spring.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.alexprokopiev.spring.config.condition.JpaCondition;
import org.springframework.context.annotation.*;

@Slf4j
@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration {

    //    @Bean
//    @ConfigurationProperties(prefix = "db")
//    public DatabaseProperties databaseProperties() {
//        return new DatabaseProperties();
//    }

    @PostConstruct
    void init() {
        log.info("jpa config enabled");
    }
}
