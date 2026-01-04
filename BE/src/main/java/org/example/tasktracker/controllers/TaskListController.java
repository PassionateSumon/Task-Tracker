package org.example.tasktracker.controllers;

import org.example.tasktracker.dto.TaskListDto;
import org.example.tasktracker.entities.TaskList;
import org.example.tasktracker.mappers.TaskListMapper;
import org.example.tasktracker.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-list")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );

        return taskListMapper.toDto(createdTaskList);
    }

    @GetMapping(path = "/{id}")
    public Optional<TaskListDto> getTaskListById(@PathVariable("id") UUID id) {
        return taskListService.getTaskList(id).map(taskListMapper::toDto);
    }

    @PutMapping(path = "/{id}")
    public TaskListDto updateTaskList(@PathVariable("id") UUID id, @RequestBody TaskListDto taskListDto) {
        TaskList updatedTaskList = taskListService.updateTaskList(id, taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(updatedTaskList);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTaskList(@PathVariable("id") UUID id) {
        taskListService.deleteTaskList(id);
    }
}
