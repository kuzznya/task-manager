package com.taskmanager.manager.impl.entity;

import com.taskmanager.common.Task;
import com.taskmanager.common.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private TaskState state;
    private String author;
    private String executor;
    @CreationTimestamp
    private Instant created;
    private Instant edited;
    private String comment;

    public Task toTask() {
        return Task.builder()
                .id(id)
                .name(name)
                .description(description)
                .state(state)
                .author(author)
                .executor(executor)
                .created(created)
                .edited(edited)
                .comment(comment)
                .build();
    }
}
