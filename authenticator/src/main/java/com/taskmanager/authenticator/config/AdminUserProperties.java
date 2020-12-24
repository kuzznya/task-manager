package com.taskmanager.authenticator.config;

import lombok.Data;

@Data
public class AdminUserProperties {
    private String username;
    private String name = "admin";
    private String password;
}
