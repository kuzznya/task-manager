package com.taskmanager.manager.api.service;

import com.taskmanager.common.Task;
import com.taskmanager.common.Worker;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task getTask(UUID id);
    List<Task> getTasksOfUser(Worker executor);

}
