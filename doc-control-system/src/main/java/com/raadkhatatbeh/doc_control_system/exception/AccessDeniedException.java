package com.raadkhatatbeh.doc_control_system.exception;

/**
 * AccessDeniedException class .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class AccessDeniedException  extends RuntimeException{

    public AccessDeniedException(String message) {
        super(message);
    }

    public  AccessDeniedException(final String format, final Object... args) {
        super(String.format(format, args));
    }
}
