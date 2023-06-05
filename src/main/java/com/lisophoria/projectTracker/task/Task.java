package com.lisophoria.projectTracker.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer taskId;
    private Integer categoryId;
    private String taskName;
    private String description;
    private Boolean status;
}
