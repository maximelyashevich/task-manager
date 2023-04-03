package com.epam.task.manager.dto;

import com.epam.task.manager.model.SubTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubTaskDto {
    private String id;
    private List<SubTask> subTasks;

    @Override
    public String toString() {
        var subTasksData = subTasks.stream()
                .map(SubTask::toString)
                .collect(Collectors.joining("\n\t\t", "\n\t\t", ""));

        return "\nSubTaskDto{" +
                "\n\tid: '" + id + '\'' +
                "\n\tsubTasks:" + subTasksData +
                "\n}";
    }
}
