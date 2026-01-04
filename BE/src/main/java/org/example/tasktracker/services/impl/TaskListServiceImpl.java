package org.example.tasktracker.services.impl;

import org.example.tasktracker.entities.TaskList;
import org.example.tasktracker.repositories.TaskListRepository;
import org.example.tasktracker.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskListServiceImpl  implements TaskListService {
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null != taskList.getId()) {
            throw new IllegalArgumentException("Id should not be passed!");
        }

        if (null == taskList.getTitle() || taskList.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task list title should be present!");
        }

        LocalDateTime now = LocalDateTime.now();

        return taskListRepository.save(
                new TaskList(
                        null,
                        taskList.getTitle(),
                        taskList.getDescription(),
                        null,
                        now,
                        now,
                        null
                )
        );
    }
}
