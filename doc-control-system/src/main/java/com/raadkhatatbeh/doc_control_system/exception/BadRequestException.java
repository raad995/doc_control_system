package com.raadkhatatbeh.doc_control_system.exception;

/**
 * BadRequestException class .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(final String message) {
        super(message);
    }

    public  BadRequestException(final String format, final Object... args) {
        super(String.format(format, args));
    }
}