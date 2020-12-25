package com.taskmanager.reporter.entity;

import com.taskmanager.common.Sprint;
import com.taskmanager.common.SprintState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate start;
    private Duration duration;
    private SprintState state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReportEntity> reports;

    public Sprint toSprint() {
        return new Sprint(id, state, start, duration);
    }
}
