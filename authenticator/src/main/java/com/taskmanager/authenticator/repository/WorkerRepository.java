package com.taskmanager.authenticator.repository;

import com.taskmanager.authenticator.entity.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, UUID> {
    Optional<WorkerEntity> findByUsername(String username);
}
