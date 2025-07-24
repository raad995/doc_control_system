package com.raadkhatatbeh.doc_control_system.exception;

/**
 * BadRequestException class .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}