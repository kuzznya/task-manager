package com.taskmanager.reporter.repository;

import com.taskmanager.common.SprintState;
import com.taskmanager.reporter.entity.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SprintRepository extends JpaRepository<SprintEntity, UUID> {
    Optional<SprintEntity> findByState(SprintState state);
}
