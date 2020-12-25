package com.taskmanager.reporter.repository;

import com.taskmanager.reporter.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, UUID> {
    ReportEntity findByAuthorAndAndSprint_Id(String author, UUID sprintId);
}
