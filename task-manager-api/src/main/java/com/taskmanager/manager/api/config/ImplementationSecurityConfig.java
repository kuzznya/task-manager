package com.taskmanager.manager.api.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface ImplementationSecurityConfig {
    void configure(HttpSecurity http) throws Exception;
}
