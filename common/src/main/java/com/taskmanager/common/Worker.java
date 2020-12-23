package com.taskmanager.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Worker {
    String name;
    Worker master;
    List<Worker> slaves;

    public Worker(@JsonProperty(value = "name", required = true) String name,
                  @JsonProperty("master") Worker master,
                  @JsonProperty("slaves") List<Worker> slaves) {
        this.name = name;
        this.master = master;
        this.slaves = slaves;
    }
}
