package com.taskmanager.manager.api.controller;

import com.taskmanager.common.Task;
import com.taskmanager.manager.api.model.TaskRequest;
import com.taskmanager.manager.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public Task getTask(@PathVariable UUID id) {
        return taskService.getTask(id);
    }

    @GetMapping("/assigned")
    public List<Task> getAssignedTasks(@AuthenticationPrincipal User executor) {
        return taskService.getAssignedTasks(executor);
    }

    @GetMapping("/created")
    public List<Task> getCreatedTasks(@AuthenticationPrincipal User author) {
        return taskService.getCreatedTasks(author);
    }

    @GetMapping("/executed")
    public List<Task> getExecutedTasks(@AuthenticationPrincipal User executor) {
        return taskService.getExecutedTasks(executor);
    }

    @GetMapping(path = "/created", params = {"created"})
    public List<Task> findCreatedTasksByCreationTime(@RequestParam Instant created,
                                                     @AuthenticationPrincipal User author) {
        return taskService.findCreatedTasksByCreationTime(author, created);
    }

    @GetMapping(path = "/assigned", params = "created")
    public List<Task> findAssignedTasksByCreationTime(@RequestParam Instant created,
                                                      @AuthenticationPrincipal User executor) {
        return taskService.findAssignedTasksByCreationTime(executor, created);
    }

    @GetMapping(path = "/created", params = {"edited"})
    public List<Task> findCreatedTasksByEditTime(@RequestParam Instant edited,
                                                 @AuthenticationPrincipal User author) {
        return taskService.findCreatedTasksByEditTime(author, edited);
    }

    @GetMapping(path = "/assigned", params = "edited")
    public List<Task> findAssignedTasksByEditTime(@RequestParam Instant edited,
                                                  @AuthenticationPrincipal User executor) {
        return taskService.findAssignedTasksByEditTime(executor, edited);
    }

    @GetMapping("/slaves/assigned")
    public List<Task> getTasksAssignedToSlaves(@AuthenticationPrincipal User master) {
        return taskService.getTasksAssignedToSlaves(master);
    }

    @PostMapping
    public Task createTask(@RequestBody TaskRequest request,
                           @AuthenticationPrincipal User author) {
        return taskService.createTask(author, request);
    }

    @PutMapping("/{id}")
    public Task editTask(@PathVariable UUID id,
                         @RequestBody TaskRequest request,
                         @AuthenticationPrincipal User author) {
        return taskService.editTask(author, id, request);
    }
}
