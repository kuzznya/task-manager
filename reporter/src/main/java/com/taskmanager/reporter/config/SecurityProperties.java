package com.taskmanager.reporter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security")
@Data
public class SecurityProperties {
    private String secret;
}
