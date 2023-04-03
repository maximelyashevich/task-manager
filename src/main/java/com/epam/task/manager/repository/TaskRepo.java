package com.epam.task.manager.repository;

import com.epam.task.manager.dto.SubTaskDto;
import com.epam.task.manager.model.Category;
import com.epam.task.manager.model.SubTask;
import com.epam.task.manager.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;


public interface TaskRepo extends MongoRepository<Task, String> {

    @Query("{ deadline: { $lt: ISODate() } }")
    List<Task> findOverdueTasks();

    @Query("{ category: { $eq: ?0 } }")
    List<Task> findByCategory(final Category category);

    @Query(value = "{ category: { $eq: ?0 } }", fields = "{ 'subTasks': 1 }")
    List<SubTaskDto> findSubTasksByCategory(final Category category);

    @Query(value = "{ _id: { $eq: ?0 } }", fields = "{ 'subTasks': 1 }")
    List<SubTaskDto> createSubTasks(final String id);

    @Query("{ name: ?0 }")
    Task findByName(final String name);

    @Query("{ name: ?0 }")
    @Update("{ $push: { 'subTasks': ?1 } }")
    void addSubTaskByName(final String name, final SubTask subTask);

    @Query("{ name: ?0 }")
    @Update("{ $set: { 'subTasks': ?1 } }")
    void replaceAllSubTask(final String name, final List<SubTask> subTasks);

    @Query("{ name: ?0 }")
    @Update("{ $pull: { 'subTasks': { 'name': ?1 } } }")
    void removeSubTaskByName(final String name, final String subTaskName);

    List<Task> findAllBy(final TextCriteria search, Sort sort);
}


