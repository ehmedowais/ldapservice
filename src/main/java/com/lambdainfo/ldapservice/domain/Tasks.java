package com.lambdainfo.ldapservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Tasks {
    public int count;
    @JsonProperty("data")
    public ArrayList<TaskDetails> tasks;

    @Data
    public static class TaskDetails {

            public boolean completed;
            public String _id;
            public String description;
            public String owner;
            public String createdAt;
            public String updatedAt;
            public int __v;

    }
}
