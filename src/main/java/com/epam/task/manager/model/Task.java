package com.epam.task.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Document(collection = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @TextIndexed
    private String description;

    private Category category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadline;

    private List<SubTask> subTasks = new ArrayList<>();

    @Override
    public String toString() {
        var subTasksData = subTasks.stream()
                .map(SubTask::toString)
                .collect(Collectors.joining("\n\t\t", "\n\t\t", ""));

        return "\nTask{" +
                "\n\tid: '" + id + '\'' +
                "\n\tname: '" + name + '\'' +
                "\n\tdescription: '" + description + '\'' +
                "\n\tcategory: " + category +
                "\n\tcreatedDate: " + createdDate +
                "\n\tdeadline: " + deadline +
                "\n\tsubTasks:" + subTasksData +
                "\n}";
    }
}
