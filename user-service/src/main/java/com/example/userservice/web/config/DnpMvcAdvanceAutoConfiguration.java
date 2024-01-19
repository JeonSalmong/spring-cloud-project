package com.example.userservice.web.config;

import com.example.userservice.web.session.SessionMap;
import com.example.userservice.web.session.SessionMapHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DnpMvcAdvanceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SessionMap sessionMapHandler() {
        return new SessionMapHandler();
    }
}
