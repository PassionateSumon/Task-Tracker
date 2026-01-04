package org.example.tasktracker.mappers;

import org.example.tasktracker.dto.TaskListDto;
import org.example.tasktracker.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
