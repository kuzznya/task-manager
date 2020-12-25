package com.taskmanager.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonSubTypes({
        @JsonSubTypes.Type(value = DailyReport.class, name = "daily"),
        @JsonSubTypes.Type(value = SprintReport.class, name = "sprint")
})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
public abstract class Report {
    private final UUID id;
    private final String author;
    private final List<UUID> tasks;
    private final String content;
    private final UUID sprint;
}
