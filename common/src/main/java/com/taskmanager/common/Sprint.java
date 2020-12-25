package com.taskmanager.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Value
public class Sprint {

    UUID id;
    SprintState state;
    LocalDate start;
    Duration duration;

    @JsonCreator
    public Sprint(@JsonProperty("id") UUID id,
                  @JsonProperty("state") SprintState state,
                  @JsonProperty("start") LocalDate start,
                  @JsonProperty("duration") Duration duration) {
        this.id = id;
        this.state = state;
        this.start = start;
        this.duration = duration;
    }
}
