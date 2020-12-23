package com.taskmanager.manager.api.controller;

import com.taskmanager.common.Worker;
import com.taskmanager.manager.api.model.TaskRequest;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping("/{id}")
    public Task getTask(@PathVariable UUID id) {
        // TODO: 23.12.2020 implement
        return null;
    }

    @GetMapping
    public List<Task> getTasksOfUser(@AuthenticationPrincipal Worker executor) {
        // TODO: 23.12.2020 implement
        return null;
    }

    @GetMapping(params = {"created"})
    public List<Task> findByCreationTime(@RequestParam Instant created) {
        // TODO: 23.12.2020 implement
        return null;
    }

    @GetMapping(params = {"edited"})
    public List<Task> findByEditTime(@RequestParam Instant edited) {
        // TODO: 23.12.2020 implement
        return null;
    }

    @GetMapping("/edited")
    public List<Task> findEditedByUser(@AuthenticationPrincipal Worker user) {
        // TODO: 23.12.2020 implement
        return null;
    }

    @GetMapping("/slaves")
    public List<Task> getTasksOfSlaves(@AuthenticationPrincipal Worker master) {
        // TODO: 23.12.2020 implement
        return null;
    }

    @PostMapping
    public Task createTask(@RequestBody TaskRequest request) {
        // TODO: 23.12.2020 implement
        return null;
    }

    @PutMapping("/{id}")
    public Task editTask(@PathVariable UUID id, @RequestBody TaskRequest request) {
        // TODO: 23.12.2020 implement
        return null;
    }
}
