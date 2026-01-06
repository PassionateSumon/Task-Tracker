package org.example.tasktracker.controllers;

import org.example.tasktracker.dto.TaskDto;
import org.example.tasktracker.entities.Task;
import org.example.tasktracker.mappers.TaskMapper;
import org.example.tasktracker.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable(name = "task_list_id") UUID id) {
        return taskService.listTasks(id).stream().map(taskMapper::toDto).toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task createdTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(createdTask);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable(name = "id") UUID id) {
        Task getTask = taskService.getTask(taskListId, id);
        return taskMapper.toDto(getTask);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID taskListId,
                              @PathVariable(name = "id") UUID id, @RequestBody TaskDto taskDto) {
        Task updatedTask = taskService.updateTask(taskListId, id, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable(name = "id") UUID id) {
        taskService.deleteTask(taskListId, id);
    }
}
