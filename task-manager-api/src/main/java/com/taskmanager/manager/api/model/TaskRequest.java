package com.taskmanager.manager.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TaskRequest {

    String name;
    String description;
    String comment;

    @JsonCreator
    public TaskRequest(@JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "description", required = true) String description,
                       @JsonProperty("comment") String comment) {
        this.name = name;
        this.description = description;
        this.comment = comment;
    }
}
