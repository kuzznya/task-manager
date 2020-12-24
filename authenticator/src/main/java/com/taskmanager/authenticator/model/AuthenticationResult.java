package com.taskmanager.authenticator.model;

import lombok.Value;

@Value
public class AuthenticationResult {
    String username;
    String token;
    String refreshToken;
}
