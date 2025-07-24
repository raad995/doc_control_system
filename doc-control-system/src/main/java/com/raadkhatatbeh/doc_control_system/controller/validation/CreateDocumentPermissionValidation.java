package com.raadkhatatbeh.doc_control_system.controller.validation;

import com.raadkhatatbeh.doc_control_system.exception.AccessDeniedException;
import com.raadkhatatbeh.doc_control_system.exception.BadRequestException;
import com.raadkhatatbeh.doc_control_system.exception.ErrorMessages;
import com.raadkhatatbeh.doc_control_system.model.DocumentModel;
import com.raadkhatatbeh.doc_control_system.model.DocumentPermissionModel;
import java.util.Optional;

/**
 * Create Document Permission Validation .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class CreateDocumentPermissionValidation {

    private static final String NAME = "name";

    /**
     * Validates the given {@link DocumentModel} and ensures the user has permission to perform the operation.
     *
     * @param model    the document model to validate
     * @param username the username performing the operation
     * @throws BadRequestException    if any required field is missing or has an invalid size
     * @throws AccessDeniedException if the user is not an admin
     */
    public static void validate(final DocumentPermissionModel model, final String username) {

        if (username == null || username.isBlank()) {
            throw new BadRequestException(ErrorMessages.MISSING_FIELD);
        }

        // Validate document name
        Optional.ofNullable(model.getUsername()).ifPresentOrElse(name -> {
            if (name.isEmpty() || name.length() < ValidationConstraints.MIN || name.length() > ValidationConstraints.MAX) {
                throw new BadRequestException(ErrorMessages.INVALID_SIZE,
                                              NAME,
                                              ValidationConstraints.MIN,
                                              ValidationConstraints.MAX);
            }
        }, () -> {
            throw new BadRequestException(ErrorMessages.MISSING_FIELD, NAME);
        });
    }
}
