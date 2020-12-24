package com.taskmanager.manager.impl.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.taskmanager.common.Worker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticatorClient {

    private final EurekaClient eurekaClient;
    private final RestTemplateBuilder restTemplateBuilder;

    public AuthenticatorClient(@Qualifier("eurekaClient") EurekaClient eurekaClient,
                               RestTemplateBuilder builder) {
        this.eurekaClient = eurekaClient;
        this.restTemplateBuilder = builder;
    }

    private String getServiceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("AUTHENTICATOR", false);
        return instance.getHomePageUrl();
    }

    public Worker getWorker(String token) {
        return restTemplateBuilder
                .rootUri(getServiceUrl())
                .setConnectTimeout(Duration.ofSeconds(20))
                .setReadTimeout(Duration.ofSeconds(20))
                .defaultHeader("Authorization", "Bearer " + token)
                .build()
                .getForObject("/authenticator/user", Worker.class);
    }

    public Worker getWorkerByUsername(String token, String username) {
        return restTemplateBuilder
                .rootUri(getServiceUrl())
                .setConnectTimeout(Duration.ofSeconds(20))
                .setReadTimeout(Duration.ofSeconds(20))
                .defaultHeader("Authorization", "Bearer " + token)
                .build()
                .getForObject(getServiceUrl() + "/authenticator/user/" + username, Worker.class);
    }

    public List<Worker> getAllSlaves(String token, String username) {
        List<?> result = restTemplateBuilder
                .rootUri(getServiceUrl())
                .setConnectTimeout(Duration.ofSeconds(20))
                .setReadTimeout(Duration.ofSeconds(20))
                .defaultHeader("Authorization", "Bearer " + token)
                .build()
                .getForObject(getServiceUrl() + "/authenticator/user/" + username + "/slaves", List.class);

        if (result == null)
            throw new RuntimeException("Result is null");

        return result
                .stream()
                .filter(obj -> obj instanceof Worker)
                .map(obj -> (Worker) obj)
                .collect(Collectors.toList());
    }
}
