package com.raadkhatatbeh.doc_control_system.controller.validation;

import com.raadkhatatbeh.doc_control_system.exception.AccessDeniedException;
import com.raadkhatatbeh.doc_control_system.exception.BadRequestException;
import com.raadkhatatbeh.doc_control_system.exception.ErrorMessages;
import com.raadkhatatbeh.doc_control_system.model.DocumentModel;
import java.util.Optional;

/**
 * CreateDocumentValidation .
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class CreateDocumentValidation {

    private static final String ADMIN = "admin";
    private static final String NAME = "name";
    private static final String CONTENT = "content";

    /**
     * Validates the given {@link DocumentModel} and ensures the user has permission to perform the operation.
     *
     * @param model    the document model to validate
     * @param username the username performing the operation
     * @throws BadRequestException    if any required field is missing or has an invalid size
     * @throws AccessDeniedException if the user is not an admin
     */
    public static void validate(final DocumentModel model, final String username) {

        if (username == null || username.isBlank()) {
            throw new BadRequestException(ErrorMessages.MISSING_FIELD);
        }

        // Only admin can perform this action
        if (!username.equals(ADMIN)) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED_ADMIN_ONLY);
        }

        // Validate document name
        Optional.ofNullable(model.getName()).ifPresentOrElse(name -> {
            if (name.isEmpty() || name.length() < ValidationConstraints.MIN || name.length() > ValidationConstraints.MAX) {
                throw new BadRequestException(ErrorMessages.INVALID_SIZE,
                                              NAME,
                                              ValidationConstraints.MIN,
                                              ValidationConstraints.MAX);
            }
        }, () -> {
            throw new BadRequestException(ErrorMessages.MISSING_FIELD, NAME);
        });

        // Validate document content
        Optional.ofNullable(model.getContent()).ifPresentOrElse(content -> {
            if (content.isEmpty() || content.length() < ValidationConstraints.MIN || content.length() > ValidationConstraints.MAX) {
                throw new BadRequestException(ErrorMessages.INVALID_SIZE,
                                              CONTENT,
                                              ValidationConstraints.MIN,
                                              ValidationConstraints.MAX);
            }
        }, () -> {
            throw new BadRequestException(ErrorMessages.MISSING_FIELD, CONTENT);
        });


    // Validate user permission
        Optional.ofNullable(model.getAccessibleUsers())
         .ifPresent(permissionModels -> {
             permissionModels
               .forEach(model1 -> CreateDocumentPermissionValidation.validate(model1, username));
         });
    }
}
