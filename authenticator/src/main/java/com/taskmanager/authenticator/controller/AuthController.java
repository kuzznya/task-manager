package com.taskmanager.authenticator.controller;

import com.taskmanager.authenticator.model.AuthenticationRequest;
import com.taskmanager.authenticator.model.AuthenticationResult;
import com.taskmanager.authenticator.model.RegistrationRequest;
import com.taskmanager.authenticator.service.AuthService;
import com.taskmanager.authenticator.service.WorkerService;
import com.taskmanager.common.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final WorkerService workerService;

    @PostMapping
    public Worker register(@RequestBody RegistrationRequest request) {
        return workerService.register(request).toWorker();
    }

    @PostMapping("/authentication")
    public AuthenticationResult authenticate(@RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/authentication/refresh")
    public AuthenticationResult refresh(Authentication authentication) {
        return authService.refresh(authentication);
    }
}
