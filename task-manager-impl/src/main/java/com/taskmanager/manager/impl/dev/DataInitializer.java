package com.taskmanager.manager.impl.dev;

import com.taskmanager.common.TaskState;
import com.taskmanager.manager.impl.entity.TaskEntity;
import com.taskmanager.manager.impl.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {

        TaskEntity task1 = new TaskEntity(null, "Task 1", "Test task 1", TaskState.ACTIVE,
                "worker1", "worker2", Instant.now(), Instant.now(), "comment");
        TaskEntity task2 = new TaskEntity(null, "Task 2", "Test task 2", TaskState.OPEN,
                "worker2", null, Instant.now(), Instant.now(), "comment 2");

        taskRepository.save(task1);
        taskRepository.save(task2);
    }
}
