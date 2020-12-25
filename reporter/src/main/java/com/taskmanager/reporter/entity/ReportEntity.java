package com.taskmanager.reporter.entity;

import com.taskmanager.common.Report;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class ReportEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String author;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<UUID> tasks;
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    private SprintEntity sprint;

    public abstract Report toReport();
}
