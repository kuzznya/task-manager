package com.taskmanager.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DailyReport extends Report {

    LocalDate date;


    @JsonCreator
    public DailyReport(@JsonProperty("id") UUID id,
                       @JsonProperty("author") String author,
                       @JsonProperty("tasks") List<UUID> tasks,
                       @JsonProperty("content") String content,
                       @JsonProperty("date") LocalDate date,
                       @JsonProperty("sprint") UUID sprint) {
        super(id, author, tasks, content, sprint);
        this.date = date;
    }
}
