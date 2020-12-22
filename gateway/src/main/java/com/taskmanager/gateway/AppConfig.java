package com.taskmanager.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ApplicationEventPublisher publisher;

    public void refreshRoutes() {
        publisher.publishEvent(new InstanceRegisteredEvent<Void>(this, null));
    }
}
