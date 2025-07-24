package com.raadkhatatbeh.doc_control_system.exception.model;

import java.time.LocalDateTime;

/**
 * BadRequestException class .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class ApiErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiErrorResponse(final int status, final String message, final LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}