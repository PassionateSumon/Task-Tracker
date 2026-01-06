package org.example.tasktracker.services;

import org.example.tasktracker.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID id);
    Task createTask(UUID taskListId, Task task);
    Task getTask(UUID taskListId, UUID id);
    Task updateTask(UUID taskListId, UUID id, Task task);
    void deleteTask(UUID taskListId, UUID id);
}
