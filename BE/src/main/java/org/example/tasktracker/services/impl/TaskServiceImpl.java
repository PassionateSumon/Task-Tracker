package org.example.tasktracker.services.impl;

import org.example.tasktracker.entities.Task;
import org.example.tasktracker.entities.TaskList;
import org.example.tasktracker.entities.TaskPriority;
import org.example.tasktracker.entities.TaskStatus;
import org.example.tasktracker.repositories.TaskListRepository;
import org.example.tasktracker.repositories.TaskRepository;
import org.example.tasktracker.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID id) {
        return taskRepository.findByTaskListId(id);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task id should not be passed!");
        }
        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title should not be empty or null!");
        }

        TaskStatus taskStatus = TaskStatus.OPEN;
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskList taskList = taskListRepository.findById(taskListId).orElse(null);
        if (null == taskList) {
            throw new IllegalArgumentException("Task list not found with this id!");
        }

        LocalDateTime now = LocalDateTime.now();

        return taskRepository.save(
                new Task(
                        null,
                        task.getTitle(),
                        task.getDescription(),
                        task.getDueDate(),
                        taskStatus,
                        taskPriority,
                        taskList,
                        now,
                        now,
                        null
                )
        );
    }

    @Override
    public Task getTask(UUID taskListId, UUID id) {
        return taskRepository.findByTaskListIdAndId(taskListId, id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with this id!"));
    }

    @Override
    public Task updateTask(UUID taskListId, UUID id, Task task) {

        TaskList taskList = taskListRepository.findById(taskListId).orElse(null);
        if (null == taskList) {
            throw new IllegalArgumentException("Task list not found with this id!");
        }

        Task currTask = taskRepository.findById(id).orElse(null);
        if (null == currTask) {
            throw new IllegalArgumentException("Task not found with this id!");
        }
        if (!id.equals(currTask.getId())) {
            throw new IllegalArgumentException("Task id should be equals to current task id");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = Optional.ofNullable(task.getStatus()).orElse(TaskStatus.OPEN);

        currTask.setTitle(task.getTitle());
        currTask.setDescription(task.getDescription());
        currTask.setDueDate(task.getDueDate());
        currTask.setPriority(taskPriority);
        currTask.setStatus(taskStatus);
        currTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(currTask);
    }

    @Override
    public void deleteTask(UUID taskListId, UUID id) {
        Task task = taskRepository.findByTaskListIdAndId(taskListId, id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found in this task list"));

        taskRepository.delete(task);
    }
}
