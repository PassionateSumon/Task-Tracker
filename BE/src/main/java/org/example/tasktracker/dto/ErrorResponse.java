package org.example.tasktracker.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
