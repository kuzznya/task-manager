package com.taskmanager.reporter.entity;

import com.taskmanager.common.DailyReport;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DailyReportEntity extends ReportEntity {

    private LocalDate date;

    @Override
    public DailyReport toReport() {
        return new DailyReport(getId(), getAuthor(), getTasks(), getContent(), date, getSprint().getId());
    }
}
