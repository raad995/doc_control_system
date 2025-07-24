package com.raadkhatatbeh.doc_control_system.exception;

import com.raadkhatatbeh.doc_control_system.exception.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

/**
 * GlobalExceptionHandler class .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link BadRequestException}.
     *
     * @param ex the exception
     * @return 400 Bad Request response
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(final BadRequestException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ResourceNotFoundException}.
     *
     * @param ex the exception
     * @return 404 Not Found response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(final ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link AccessDeniedException}.
     *
     * @param ex the exception
     * @return 403 Forbidden response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(final AccessDeniedException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles uncaught exceptions.
     *
     * @param ex the exception
     * @return 500 Internal Server Error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalServerError(final Exception ex) {
        return buildErrorResponse(
                "An unexpected error occurred. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Helper method to build a standardized {@link ApiErrorResponse}.
     *
     * @param message error message
     * @param status  HTTP status
     * @return a {@link ResponseEntity} with {@link ApiErrorResponse}
     */
    private ResponseEntity<ApiErrorResponse> buildErrorResponse(final String message, final HttpStatus status) {
        ApiErrorResponse error = new ApiErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(error);
    }
}