package com.taskmanager.manager.api.service;

import com.taskmanager.common.Task;
import com.taskmanager.manager.api.model.TaskRequest;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TaskService {

    Task getTask(UUID id);
    Task createTask(User author, TaskRequest request);
    Task editTask(User author, UUID id, TaskRequest request);

    List<Task> getAssignedTasks(User executor);
    List<Task> getCreatedTasks(User author);
    List<Task> getExecutedTasks(User executor);
    List<Task> findCreatedTasksByCreationTime(User author, Instant created);
    List<Task> findAssignedTasksByCreationTime(User executor, Instant created);
    List<Task> findCreatedTasksByEditTime(User author, Instant edited);
    List<Task> findAssignedTasksByEditTime(User executor, Instant edited);
    List<Task> getTasksAssignedToSlaves(User master);
}
