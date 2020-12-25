package com.taskmanager.common;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SprintReport extends Report {

    List<SprintReport> slavesReports;

    @JsonCreator
    public SprintReport(@JsonProperty("id") UUID id,
                        @JsonProperty("author") String author,
                        @JsonProperty("content") String content,
                        @JsonProperty("sprint") UUID sprint,
                        @JsonProperty("sprintReports") List<SprintReport> slavesReports) {
        super(id, author,
                slavesReports.stream()
                        .flatMap(report -> report.getTasks().stream())
                        .distinct()
                        .collect(Collectors.toList()),
                content,
                sprint);
        this.slavesReports = slavesReports;
    }
}
