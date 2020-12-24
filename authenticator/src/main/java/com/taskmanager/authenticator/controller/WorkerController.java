package com.taskmanager.authenticator.controller;

import com.taskmanager.authenticator.entity.WorkerEntity;
import com.taskmanager.authenticator.service.WorkerService;
import com.taskmanager.common.Worker;
import com.taskmanager.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping
    public Worker getWorker(@AuthenticationPrincipal WorkerEntity worker) {
        return worker.toWorker();
    }

    @GetMapping(path = "/{username}")
    public Worker getWorkerByUsername(@PathVariable String username) {
        return workerService.findWorkerByUsername(username)
                .orElseThrow(NotFoundException::new)
                .toWorker();
    }

    @PostMapping("/{username}/master/{master}")
    @PreAuthorize("hasRole('ADMIN')")
    public void setMaster(@PathVariable String username,
                            @PathVariable String master) {
        WorkerEntity worker = workerService.findWorkerByUsername(username)
                .orElseThrow(NotFoundException::new);
        WorkerEntity masterEntity = workerService.findWorkerByUsername(master)
                .orElseThrow(NotFoundException::new);
        workerService.setMaster(worker, masterEntity);
    }

    @PostMapping("/{username}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public void setAdmin(@PathVariable String username) {
        WorkerEntity worker = workerService.findWorkerByUsername(username)
                .orElseThrow(NotFoundException::new);
        workerService.setAdmin(worker);
    }

    @GetMapping("/{username}/slaves")
    public List<Worker> getAllSlaves(@PathVariable String username) {
        WorkerEntity worker = workerService.findWorkerByUsername(username)
                .orElseThrow(NotFoundException::new);

        return workerService.getAllSlaves(worker)
                .stream()
                .map(WorkerEntity::toWorker)
                .collect(Collectors.toList());
    }
}
