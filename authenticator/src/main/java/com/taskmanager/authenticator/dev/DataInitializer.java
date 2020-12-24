package com.taskmanager.authenticator.dev;

import com.taskmanager.authenticator.config.AdminUserProperties;
import com.taskmanager.authenticator.config.SecurityProperties;
import com.taskmanager.authenticator.entity.WorkerEntity;
import com.taskmanager.authenticator.model.RegistrationRequest;
import com.taskmanager.authenticator.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final WorkerService workerService;
    private final SecurityProperties securityProperties;

    @Override
    public void run(String... args) throws Exception {
        workerService.register(new RegistrationRequest("worker1", "First Worker", "worker1"));
        workerService.register(new RegistrationRequest("worker2", "Second Worker", "worker2"));
        workerService.register(new RegistrationRequest("worker3", "Third Worker", "worker3"));
        AdminUserProperties adminProperties = securityProperties.getAdmin();
        WorkerEntity admin = workerService.register(new RegistrationRequest(
                adminProperties.getUsername(),
                adminProperties.getName(),
                adminProperties.getPassword()));
        workerService.setAdmin(admin);
    }
}
