package com.taskmanager.reporter.service;

import com.taskmanager.common.Sprint;
import com.taskmanager.common.SprintState;
import com.taskmanager.reporter.entity.SprintEntity;
import com.taskmanager.reporter.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;

    public Sprint getSprint(UUID id) {
        return sprintRepository.getOne(id).toSprint();
    }

    public SprintEntity initializeSprint(int days) {
        findActiveSprint().ifPresent(sprint -> {
            throw new RuntimeException("Sprint " + sprint.getId() + " is active");
        });
        SprintEntity sprint = new SprintEntity(null, LocalDate.now(), Duration.ofDays(days), SprintState.ACTIVE, new ArrayList<>());
        return sprintRepository.save(sprint);
    }

    public Optional<SprintEntity> findActiveSprint() {
        return sprintRepository.findByState(SprintState.ACTIVE);
    }
}
