package com.taskmanager.authenticator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;

@ConfigurationProperties("security")
@Validated
@Data
public class SecurityProperties {
    private Duration tokenLifetime = Duration.ofHours(1);
    private Duration refreshTokenLifetime = Duration.ofDays(30);
    @NotEmpty
    @NotNull
    @Size(min = 14)
    private String secret;
    private AdminUserProperties admin;
}
