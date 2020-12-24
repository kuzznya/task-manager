package com.taskmanager.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {

    UUID id;
    String name;
    String description;
    TaskState state;
    String author;
    String executor;
    Instant created;
    Instant edited;
    String comment;

    @JsonCreator
    public Task(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("state") TaskState state,
                @JsonProperty("author") String author,
                @JsonProperty("executor") String executor,
                @JsonProperty("creationTime") Instant created,
                @JsonProperty("lastEditTime") Instant edited,
                @JsonProperty("comment") String comment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.author = author;
        this.executor = executor;
        this.created = created;
        this.edited = edited;
        this.comment = comment;
    }
}
