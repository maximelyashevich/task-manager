package com.epam.task.manager;

import com.epam.task.manager.model.Category;
import com.epam.task.manager.model.SubTask;
import com.epam.task.manager.model.Task;
import com.epam.task.manager.service.DBGenerator;
import com.epam.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.List;


@SpringBootApplication
@EnableMongoAuditing
public class TaskApplication implements CommandLineRunner {

    private static final String TASK_NAME = "Create new task2";

    @Autowired
    private DBGenerator dbGenerator;

    @Autowired
    private TaskService taskService;

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Override
    public void run(String... args) {
        dbGenerator.clearData();
        dbGenerator.generateData();

        // 1. Display on console all tasks.
        var tasks = taskService.findAll();
        tasks.forEach(System.out::println);

        // 2. Display overdue tasks.
        var tasks2 = taskService.findOverdueTasks();
        tasks2.forEach(System.out::println);

        // 3. Display all tasks with a specific category (query parameter).
        var tasks3 = taskService.findByCategory(Category.MEDIUM_PRIORITY);
        tasks3.forEach(System.out::println);

        // 4. Display all subtasks related to tasks with a specific category (query parameter).
        var tasks4 = taskService.findSubtasksByCategory(Category.MEDIUM_PRIORITY);
        tasks4.forEach(System.out::println);

        // 5. Perform insert/update/delete of the task.
        var task = taskService.insert(
                Task.builder()
                        .name("Test task")
                        .category(Category.MEDIUM_PRIORITY)
                        .subTasks(List.of(SubTask.builder().build()))
                        .build()
        );
        System.out.println(task);

        task.setCategory(Category.HIGH_PRIORITY);
        var updatedTask = taskService.update(task);
        System.out.println(updatedTask);

        taskService.delete(task);

        // 6. Perform insert/update/delete all subtasks of a given task (query parameter).
        var customSubTask = SubTask.builder().name("123").description("12345").build();
        taskService.addSubTaskByName(TASK_NAME, customSubTask);

        var customSubTask2 = SubTask.builder().name("124").description("12345").build();
        taskService.updateSubTaskByName(TASK_NAME, "Create new task name", customSubTask2);

        taskService.removeSubTaskByName(TASK_NAME, "124");

        taskService.replaceAllSubTask(TASK_NAME, List.of(customSubTask, customSubTask2));

        // 7. Support full-text search by word in the task description.
        var searchCriteria = new TextCriteria().matchingAny("consider");
        var searchResults = taskService.searchByDescription(searchCriteria);

        System.out.println("\n7. Support full-text search by word in the task description ('consider'):");
        searchResults.forEach(System.out::println);

        // 8. Support full-text search by a sub-task name.
        var searchCriteria2 = new TextCriteria().matchingAny("reviewed");
        var searchResults2 = taskService.searchByDescription(searchCriteria2);

        System.out.println("\n8. Support full-text search by a sub-task name ('reviewed'):");
        searchResults2.forEach(System.out::println);
    }
}




