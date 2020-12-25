package com.taskmanager.reporter.service;

import com.taskmanager.common.DailyReport;
import com.taskmanager.common.Report;
import com.taskmanager.common.SprintReport;
import com.taskmanager.common.Worker;
import com.taskmanager.reporter.entity.DailyReportEntity;
import com.taskmanager.reporter.entity.SprintEntity;
import com.taskmanager.reporter.entity.SprintReportEntity;
import com.taskmanager.reporter.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SprintService sprintService;
    private final AuthenticatorClient authenticatorClient;
    private final ReportRepository reportRepository;

    public Report getReport(UUID id) {
        return reportRepository.getOne(id).toReport();
    }

    public DailyReport saveDailyReport(User author, DailyReport report) {
        DailyReportEntity entity = new DailyReportEntity();
        entity.setAuthor(author.getUsername());
        entity.setContent(report.getContent());
        entity.setTasks(report.getTasks());
        entity.setDate(LocalDate.now());
        SprintEntity sprintEntity = sprintService.findActiveSprint()
                .orElseGet(() -> sprintService.initializeSprint(14));
        entity.setSprint(sprintEntity);
        return reportRepository.save(entity).toReport();
    }

    public SprintReport saveSprintReport(User author, SprintReport report) {
        SprintEntity sprintEntity = sprintService.findActiveSprint()
                .orElseGet(() -> sprintService.initializeSprint(14));

        SprintReportEntity entity = new SprintReportEntity();
        entity.setAuthor(author.getUsername());
        entity.setSprint(sprintEntity);
        entity.setSlavesReports(getSlavesSprintReports(author, report.getSprint()));
        entity.setContent(report.getContent());
        entity.setTasks(report.getTasks());
        return reportRepository.save(entity).toReport();
    }

    public SprintReport getDraft(User author) {
        SprintEntity sprintEntity = sprintService.findActiveSprint()
                .orElseGet(() -> sprintService.initializeSprint(14));

        List<SprintReport> slavesReports = getSlavesSprintReports(author, sprintEntity.getId())
                .stream()
                .map(SprintReportEntity::toReport)
                .collect(Collectors.toList());

        return new SprintReport(null, author.getUsername(), "Sprint report draft", sprintEntity.getId(), slavesReports);
    }

    private List<SprintReportEntity> getSlavesSprintReports(User author, UUID sprintId) {
        List<Worker> slaves = authenticatorClient.getAllSlaves(author.getPassword(), author.getUsername());
        return slaves.stream()
                .flatMap(slave -> reportRepository.findByAuthorAndSprint_Id(slave.getUsername(), sprintId).stream())
                .filter(reportEntity -> reportEntity instanceof SprintReportEntity)
                .map(reportEntity -> (SprintReportEntity) reportEntity)
                .collect(Collectors.toList());
    }

}
