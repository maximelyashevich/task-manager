package com.epam.task.manager.service;

import com.epam.task.manager.dto.SubTaskDto;
import com.epam.task.manager.model.Category;
import com.epam.task.manager.model.SubTask;
import com.epam.task.manager.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    List<Task> findOverdueTasks();

    List<Task> findByCategory(final Category category);

    List<SubTaskDto> findSubtasksByCategory(final Category category);

    Task insert(final Task task);

    Task update(final Task task);

    void delete(final Task task);

    Task findByName(final String name);

    void addSubTaskByName(final String name, final SubTask subTask);

    void updateSubTaskByName(final String name, final String subTaskName, final SubTask subTask);

    void removeSubTaskByName(final String name, final String subTaskName);

    void replaceAllSubTask(final String name, final List<SubTask> subTasks);

    List<Task> searchByDescription(final TextCriteria search);
}
