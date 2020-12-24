package com.taskmanager.manager.impl.repository;

import com.taskmanager.common.TaskState;
import com.taskmanager.manager.impl.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByAuthor(String author);
    List<TaskEntity> findByExecutor(String executor);
    List<TaskEntity> findByExecutorAndState(String executor, TaskState state);
    List<TaskEntity> findByAuthorAndCreatedAfter(String author, Instant creationTime);
    List<TaskEntity> findByExecutorAndCreatedAfter(String executor, Instant creationTime);
    List<TaskEntity> findByAuthorAndEditedAfter(String author, Instant editTime);
    List<TaskEntity> findByExecutorAndEditedAfter(String executor, Instant editTime);
}
