package org.example.tasktracker.dto;

import org.example.tasktracker.entities.TaskPriority;
import org.example.tasktracker.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status,
        TaskPriority priority
) {

}
