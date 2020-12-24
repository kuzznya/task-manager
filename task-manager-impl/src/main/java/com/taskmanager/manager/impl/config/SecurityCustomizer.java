package com.taskmanager.manager.impl.config;

import com.taskmanager.manager.api.config.ImplementationSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityCustomizer implements ImplementationSecurityConfig {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // configure security if required
    }
}
