package com.epam.task.manager.service.impl;

import com.epam.task.manager.dto.SubTaskDto;
import com.epam.task.manager.model.Category;
import com.epam.task.manager.model.SubTask;
import com.epam.task.manager.model.Task;
import com.epam.task.manager.repository.TaskRepo;
import com.epam.task.manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final String BEFORE_PREFIX = "\nBefore: ";
    private static final String AFTER_PREFIX = "\nAfter: ";

    private final TaskRepo taskRepo;

    @Override
    public List<Task> findAll() {
        System.out.println("\n1. Display on console all tasks:");
        return taskRepo.findAll();
    }

    @Override
    public List<Task> findOverdueTasks() {
        System.out.println("\n2. Display overdue tasks:");
        return taskRepo.findOverdueTasks();
    }

    @Override
    public List<Task> findByCategory(final Category category) {
        System.out.println("\n3. Display all tasks with a specific category (query parameter):");
        return taskRepo.findByCategory(category);
    }

    @Override
    public List<SubTaskDto> findSubtasksByCategory(final Category category) {
        System.out.println("\n4. Display all subtasks related to tasks with a specific category (query parameter):");
        return taskRepo.findSubTasksByCategory(category);
    }

    @Override
    public Task insert(final Task task) {
        System.out.println("\n5.1 Create a new task:");
        return taskRepo.insert(task);
    }

    @Override
    public Task update(final Task task) {
        System.out.println("\n5.2 Update the task:");
        return taskRepo.save(task);
    }

    @Override
    public void delete(final Task task) {
        System.out.println("\n5.3 Delete the task:");
        taskRepo.delete(task);
        System.out.println("deleted");
    }

    @Override
    public Task findByName(final String name) {
        return taskRepo.findByName(name);
    }

    @Transactional
    @Override
    public void addSubTaskByName(final String name, final SubTask subTask) {
        System.out.println("\n6.1 Create a new subtask:");

        var beforeTask = findByName(name);
        System.out.println(BEFORE_PREFIX + beforeTask);

        taskRepo.addSubTaskByName(name, subTask);

        var afterTask = findByName(name);
        System.out.println(AFTER_PREFIX + afterTask);
    }

    @Override
    public void updateSubTaskByName(String name, String subTaskName, SubTask subTask) {
        System.out.println("\n6.2 Update the new subtask:");

        var beforeTask = findByName(name);
        System.out.println(BEFORE_PREFIX + beforeTask);

        taskRepo.removeSubTaskByName(name, subTaskName);
        taskRepo.addSubTaskByName(name, subTask);

        var afterTask = findByName(name);
        System.out.println(AFTER_PREFIX + afterTask);
    }

    @Override
    public void removeSubTaskByName(String name, String subTaskName) {
        System.out.println("\n6.3 Remove the subtask:");

        var beforeTask = findByName(name);
        System.out.println(BEFORE_PREFIX + beforeTask);

        taskRepo.removeSubTaskByName(name, subTaskName);

        var afterTask = findByName(name);
        System.out.println(AFTER_PREFIX + afterTask);
    }

    @Override
    public void replaceAllSubTask(String name, List<SubTask> subTasks) {
        System.out.println("\n6.4 Replace all subtasks:");

        var beforeTask = findByName(name);
        System.out.println(BEFORE_PREFIX + beforeTask);

        taskRepo.replaceAllSubTask(name, subTasks);

        var afterTask = findByName(name);
        System.out.println(AFTER_PREFIX + afterTask);
    }

    @Override
    public List<Task> searchByDescription(final TextCriteria search) {
        return taskRepo.findAllBy(search, Sort.unsorted());
    }
}
