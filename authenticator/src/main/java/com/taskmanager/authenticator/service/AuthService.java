package com.taskmanager.authenticator.service;

import com.taskmanager.authenticator.config.JwtTokenUtil;
import com.taskmanager.authenticator.entity.WorkerEntity;
import com.taskmanager.authenticator.model.AuthenticationRequest;
import com.taskmanager.authenticator.model.AuthenticationResult;
import com.taskmanager.common.exception.AccessDeniedException;
import com.taskmanager.common.exception.ServiceAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WorkerService workerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;

    public AuthenticationResult authenticate(AuthenticationRequest request) {
        WorkerEntity worker = workerService.findWorkerByUsername(request.getUsername())
                .orElseThrow(ServiceAuthException::new);

        if (!passwordEncoder.matches(request.getPassword(), worker.getPassword()))
            throw new AccessDeniedException();

        String token = tokenUtil.generateToken(worker.toUser());
        String refreshToken = tokenUtil.generateRefreshToken(worker.toUser());

        return new AuthenticationResult(worker.getUsername(), token, refreshToken);
    }

    public AuthenticationResult refresh(Authentication authentication) {
        String refreshToken = (String) authentication.getCredentials();
        WorkerEntity worker = (WorkerEntity) authentication.getPrincipal();
        String token = tokenUtil.generateToken(worker.toUser());
        return new AuthenticationResult(worker.getUsername(), token, refreshToken);
    }
}
