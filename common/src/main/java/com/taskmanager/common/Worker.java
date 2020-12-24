package com.taskmanager.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Worker {
    String username;
    String name;
    String master;
    List<String> slaves;

    @JsonCreator
    public Worker(@JsonProperty(value = "username", required = true) String username,
                  @JsonProperty(value = "name", required = true) String name,
                  @JsonProperty("master") String master,
                  @JsonProperty("slaves") List<String> slaves) {
        this.username = username;
        this.name = name;
        this.master = master;
        this.slaves = slaves;
    }
}
