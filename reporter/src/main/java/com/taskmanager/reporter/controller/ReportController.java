package com.taskmanager.reporter.controller;

import com.taskmanager.common.DailyReport;
import com.taskmanager.common.Report;
import com.taskmanager.common.SprintReport;
import com.taskmanager.reporter.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{id}")
    public Report getReport(@PathVariable UUID id) {
        return reportService.getReport(id);
    }

    @PostMapping("/daily")
    public DailyReport saveDailyReport(@RequestBody DailyReport report,
                                       @AuthenticationPrincipal User author) {
        return reportService.saveDailyReport(author, report);
    }

    @PostMapping("/sprint")
    public SprintReport saveSprintReport(@RequestBody SprintReport report,
                                         @AuthenticationPrincipal User author) {
        return reportService.saveSprintReport(author, report);
    }

    @GetMapping("/sprint/draft")
    public SprintReport getSprintReportDraft(@AuthenticationPrincipal User author) {
        return reportService.getDraft(author);
    }

}
