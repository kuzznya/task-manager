package com.taskmanager.manager.impl.service;

import com.taskmanager.common.Task;
import com.taskmanager.common.TaskState;
import com.taskmanager.common.Worker;
import com.taskmanager.common.exception.AccessDeniedException;
import com.taskmanager.manager.api.model.TaskRequest;
import com.taskmanager.manager.api.service.TaskService;
import com.taskmanager.manager.impl.entity.TaskEntity;
import com.taskmanager.manager.impl.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AuthenticatorClient authenticatorClient;

    @Override
    public Task getTask(UUID id) {
        return taskRepository.getOne(id).toTask();
    }

    @Override
    public Task createTask(User author, TaskRequest request) {
        TaskEntity entity = new TaskEntity(
                null,
                request.getName(),
                request.getDescription(),
                TaskState.OPEN,
                author.getUsername(),
                request.getExecutor(),
                Instant.now(),
                Instant.now(),
                request.getComment());
        return taskRepository.save(entity).toTask();
    }

    @Override
    public Task editTask(User author, UUID id, TaskRequest request) {
        TaskEntity entity = taskRepository.getOne(id);

        boolean isAuthor = false;
        boolean isExecutor = false;

        if (entity.getAuthor().equals(author.getUsername()))
            isAuthor = true;
        else if (entity.getExecutor() != null && entity.getExecutor().equals(author.getUsername()))
            isExecutor = true;

        if (!isAuthor && !isExecutor)
            throw new AccessDeniedException();

        if (isAuthor && request.getName() != null)
            entity.setName(request.getName());
        if (isAuthor && request.getDescription() != null)
            entity.setDescription(request.getDescription());
        if (isAuthor && entity.getExecutor() == null && request.getExecutor() != null) {
            Worker worker = authenticatorClient.getWorker(author.getPassword());
            if (worker.getSlaves()
                    .stream()
                    .anyMatch(slave -> slave.equals(request.getExecutor())))
                entity.setExecutor(request.getExecutor());
            else
                throw new RuntimeException("Cannot assign task");
        }
        if (request.getComment() != null)
            entity.setComment(request.getComment());
        if (request.getState() != null)
            entity.setState(request.getState());

        entity.setEdited(Instant.now());

        return taskRepository.save(entity).toTask();
    }

    @Override
    public List<Task> getAssignedTasks(User executor) {
        return taskRepository.findByExecutor(executor.getUsername())
                .stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getCreatedTasks(User author) {
        return taskRepository.findByAuthor(author.getUsername())
                .stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getExecutedTasks(User executor) {
        return taskRepository.findByExecutorAndState(executor.getUsername(), TaskState.RESOLVED)
                .stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findCreatedTasksByCreationTime(User author, Instant created) {
        return taskRepository.findByAuthorAndCreatedAfter(author.getUsername(), created)
                .stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAssignedTasksByCreationTime(User executor, Instant created) {
        return taskRepository.findByExecutorAndCreatedAfter(executor.getUsername(), created)
                .stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findCreatedTasksByEditTime(User author, Instant edited) {
        return taskRepository.findByAuthorAndEditedAfter(author.getUsername(), edited)
                .stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAssignedTasksByEditTime(User executor, Instant edited) {
        return taskRepository.findByExecutorAndEditedAfter(executor.getUsername(), edited)
                .stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksAssignedToSlaves(User master) {
        List<Worker> slaves = authenticatorClient.getAllSlaves(master.getPassword(), master.getUsername());
        return slaves.stream()
                .map(Worker::getUsername)
                .flatMap(executor -> taskRepository
                        .findByExecutor(executor)
                        .stream()
                        .map(TaskEntity::toTask))
                .collect(Collectors.toList());
    }
}
