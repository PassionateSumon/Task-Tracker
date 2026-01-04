package org.example.tasktracker.mappers.impl;

import org.example.tasktracker.dto.TaskListDto;
import org.example.tasktracker.entities.Task;
import org.example.tasktracker.entities.TaskList;
import org.example.tasktracker.entities.TaskStatus;
import org.example.tasktracker.mappers.TaskListMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {
    TaskMapperImpl taskMapper;

    public TaskListMapperImpl(TaskMapperImpl taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(task -> task.stream()
                            .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null),
                null,
                null,
                null

        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        System.out.println("tasks => " + taskList.getTasks());
        System.out.println(calculateTaskListProgress(taskList.getTasks()));
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                                .map(List::size)
                .orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::toDto)
                                .toList())
                        .orElse(null)
        );
    }

    private double calculateTaskListProgress(List<Task> tasks) {
        if (tasks == null) {
            return 0.0;
        }

        long closedTaskCount = tasks.stream()
                .filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();
        System.out.println("tasks closed => " + closedTaskCount);

        return (double) closedTaskCount / tasks.size();
    }
}
