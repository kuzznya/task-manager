package com.taskmanager.reporter.entity;

import com.taskmanager.common.SprintReport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SprintReportEntity extends ReportEntity {

    @OneToMany
    List<SprintReportEntity> slavesReports;

    @Override
    public SprintReport toReport() {
        return new SprintReport(getId(), getAuthor(), getContent(), getSprint().getId(),
                slavesReports.stream().map(SprintReportEntity::toReport).collect(Collectors.toList()));
    }
}
