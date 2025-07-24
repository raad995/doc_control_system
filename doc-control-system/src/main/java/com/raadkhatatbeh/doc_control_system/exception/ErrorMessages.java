package com.raadkhatatbeh.doc_control_system.exception;

/**
 * ErrorCode class .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class ErrorMessages {

    public static final String INVALID_SIZE = "%s must be between %s and %s characters and not empty.";
    public static final String MISSING_FIELD = "Required field is missing.";
    public static final String INVALID_VALUE = "Invalid value for field: %s";
    public static final String RESOURCE_NOT_FOUND = "Resource not found with id";

   //ACCESS_DENIED
    public static final String ACCESS_DENIED_ADMIN_ONLY = "Only admin can perform this action.";
    public static final String ACCESS_DENIED_ADMIN_OR_HAS_PERMISSION = "Only admin or users with %s permission can perform this action.";

}
