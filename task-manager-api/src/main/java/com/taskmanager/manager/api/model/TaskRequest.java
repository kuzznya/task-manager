package com.taskmanager.manager.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskmanager.common.TaskState;
import lombok.Value;

@Value
public class TaskRequest {

    String name;
    String description;
    String executor;
    String comment;
    TaskState state;

    @JsonCreator
    public TaskRequest(@JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("executor") String executor,
                       @JsonProperty("comment") String comment,
                       @JsonProperty("state") TaskState state) {
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.comment = comment;
        this.state = state;
    }
}
