package com.taskmanager.authenticator.service;

import com.taskmanager.authenticator.entity.WorkerEntity;
import com.taskmanager.authenticator.model.RegistrationRequest;
import com.taskmanager.authenticator.repository.WorkerRepository;
import com.taskmanager.common.WorkerRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;

    public WorkerEntity register(RegistrationRequest request) {

        WorkerEntity worker = new WorkerEntity(
                request.getUsername(),
                request.getName(),
                passwordEncoder.encode(request.getPassword()));

        return workerRepository.save(worker);
    }

    public Optional<WorkerEntity> findWorkerByUsername(String username) {
        return workerRepository.findByUsername(username);
    }

    public void setMaster(WorkerEntity worker, WorkerEntity master) {
        worker.setMaster(master);
        workerRepository.save(worker);
    }

    public void setAdmin(WorkerEntity worker) {
        worker.setRole(WorkerRole.ADMIN);
        workerRepository.save(worker);
    }

    public List<WorkerEntity> getAllSlaves(WorkerEntity worker) {
        List<WorkerEntity> slaves = new ArrayList<>(worker.getSlaves());

        for (WorkerEntity slave : worker.getSlaves())
            slaves.addAll(getAllSlaves(slave));

        return slaves;
    }
}
