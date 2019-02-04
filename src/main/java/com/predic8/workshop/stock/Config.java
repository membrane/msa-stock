package com.predic8.workshop.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(Object.class, IgnoreConfig.class);
        return mapper;
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private abstract class IgnoreConfig { }
}
