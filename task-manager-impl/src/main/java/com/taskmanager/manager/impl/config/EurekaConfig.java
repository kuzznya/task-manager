package com.taskmanager.manager.impl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "eureka.instance")
public class EurekaConfig {

    private String hostname;
    private boolean nonSecurePortEnabled;
    private int nonSecurePort;
    private boolean securePortEnabled;
    private int securePort;

    @Bean
    @Profile("heroku")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
        b.setHostname(hostname);
        b.setNonSecurePortEnabled(nonSecurePortEnabled);
        if (nonSecurePortEnabled)
            b.setNonSecurePort(nonSecurePort);
        b.setSecurePortEnabled(securePortEnabled);
        if (securePortEnabled)
            b.setSecurePort(securePort);
        return b;
    }
}
