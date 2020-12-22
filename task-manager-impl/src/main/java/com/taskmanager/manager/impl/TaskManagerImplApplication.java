package com.taskmanager.manager.impl;

import com.taskmanager.manager.api.EnableTaskManagerApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTaskManagerApi
public class TaskManagerImplApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerImplApplication.class, args);
	}

}
