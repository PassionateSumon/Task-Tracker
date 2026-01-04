package org.example.tasktracker.mappers;

import org.example.tasktracker.dto.TaskDto;
import org.example.tasktracker.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
