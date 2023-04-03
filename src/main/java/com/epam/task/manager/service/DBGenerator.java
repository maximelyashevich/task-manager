package com.epam.task.manager.service;

import com.epam.task.manager.model.Category;
import com.epam.task.manager.model.SubTask;
import com.epam.task.manager.model.Task;
import com.epam.task.manager.repository.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DBGenerator {

    private final TaskRepo taskRepo;

    public void generateData() {
        var subTask1 = SubTask.builder()
                .name("Create new task name")
                .description("This description should be updated.")
                .build();

        var subTask2 = SubTask.builder()
                .name("Create new task description")
                .description("Empty")
                .build();

        var subTask3 = SubTask.builder()
                .name("Review new task")
                .description("To be discussed.")
                .build();

        var task1 = Task.builder()
                .name("Create new task1")
                .description("You need to create a new task.")
                .createdDate(LocalDateTime.now().minus(Period.ofDays(5)))
                .deadline(LocalDateTime.now().plus(Period.ofDays(1)))
                .category(Category.MEDIUM_PRIORITY)
                .subTasks(
                        List.of(subTask1, subTask2)
                )
                .build();

        var task2 = Task.builder()
                .name("Create new task2")
                .description("To be considered: task 1 as an example.")
                .createdDate(LocalDateTime.now().minus(Period.ofDays(3)))
                .deadline(LocalDateTime.now().minus(Period.ofDays(1)))
                .category(Category.HIGH_PRIORITY)
                .subTasks(
                        List.of(subTask1)
                )
                .build();

        var task3 = Task.builder()
                .name("Create new task3")
                .description("Consider task 1 or task 2 as an example.")
                .createdDate(LocalDateTime.now().minus(Period.ofDays(2)))
                .deadline(LocalDateTime.now().minus(Period.ofDays(1)))
                .category(Category.MEDIUM_PRIORITY)
                .subTasks(
                        List.of(subTask1, subTask3)
                )
                .build();

        taskRepo.saveAll(
                List.of(task1, task2, task3)
        );
    }

    public void clearData() {
        taskRepo.deleteAll();
    }
}
