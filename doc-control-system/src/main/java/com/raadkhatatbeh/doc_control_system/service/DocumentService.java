package com.raadkhatatbeh.doc_control_system.service;

import com.raadkhatatbeh.doc_control_system.controller.validation.CreateDocumentPermissionValidation;
import com.raadkhatatbeh.doc_control_system.controller.validation.CreateDocumentValidation;
import com.raadkhatatbeh.doc_control_system.exception.AccessDeniedException;
import com.raadkhatatbeh.doc_control_system.exception.BadRequestException;
import com.raadkhatatbeh.doc_control_system.exception.ErrorMessages;
import com.raadkhatatbeh.doc_control_system.exception.ResourceNotFoundException;
import com.raadkhatatbeh.doc_control_system.model.DocumentModel;
import com.raadkhatatbeh.doc_control_system.model.DocumentPermissionModel;
import com.raadkhatatbeh.doc_control_system.model.PermissionType;
import com.raadkhatatbeh.doc_control_system.modelMapper.DocumentModelMapper;
import com.raadkhatatbeh.doc_control_system.modelMapper.DocumentPermissionModelMapper;
import com.raadkhatatbeh.doc_control_system.repo.DocumentRepo;
import com.raadkhatatbeh.doc_control_system.repo.entity.Document;
import com.raadkhatatbeh.doc_control_system.repo.entity.DocumentPermission;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Document Service.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
@Service
public class DocumentService {

    private final DocumentRepo documentRepo;

    private static final String ADMIN = "admin";

    /**
     * Constructs a new {@code DocumentService} with the provided {@link DocumentRepo}.
     *
     * @param documentRepo the repository used to manage document data access
     */
    public DocumentService(final DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }

    /**
     * Creates a new document after validating the input model and user permissions.
     *
     * @param model    the {@link DocumentModel} containing the document data
     * @param username the username of the creator
     * @return the saved {@link DocumentModel}
     * @throws BadRequestException if validation fails
     * @throws AccessDeniedException if the user is not authorized
     */
    public DocumentModel createDocument(final DocumentModel model, final String username) {

        CreateDocumentValidation.validate(model, username);

        model.setCreatedBy(username);
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());
        Document document = DocumentModelMapper.toEntity(model);

        return DocumentModelMapper.toModel(documentRepo.save(document));
    }

    /**
     * Retrieves a list of documents accessible by the given user.
     *
     * @param username the username of the requester
     * @return a list of {@link DocumentModel} instances accessible by the user
     */
    public List<DocumentModel> getDocuments(final String username) {

        if (ADMIN.equals(username)) {
            return documentRepo
              .findAll()
              .stream()
              .map(DocumentModelMapper::toModel).toList();
        }

        // Only documents where user has READ permission
        List<Document> docs = documentRepo.findByUsernameAndPermission(username, PermissionType.READ);
        return docs.stream().map(DocumentModelMapper::toModel).toList();
    }

    /**
     * Retrieves a single document by its ID if the user is authorized to access it.
     *
     * @param documentId the ID of the document to retrieve
     * @param username   the username of the requester
     * @return the {@link DocumentModel} representation of the requested document
     * @throws EntityNotFoundException if the document does not exist
     * @throws AccessDeniedException   if the user does not have READ permission
     */
    public DocumentModel getDocument(final Long documentId, final String username) {

        Document doc = this.findById(documentId);
        checkPermission(doc, username, PermissionType.READ);
        return DocumentModelMapper.toModel(doc);
    }

    /**
     * Checks whether the given user has the required permission on the specified document.
     *
     * @param doc               the document to check permissions against
     * @param username          the username of the requester
     * @param requiredPermission the required {@link PermissionType} (e.g., READ or WRITE)
     * @throws AccessDeniedException if the user lacks the required permission
     */
    private void checkPermission(final Document doc, final String username, final PermissionType requiredPermission) {

        if (ADMIN.equals(username)) return;

        boolean hasPermission = doc.getAccessibleUsers().stream()
                .anyMatch(p -> p.getUsername().equals(username)
                  && p.getPermissionType().equals(requiredPermission));

        if (!hasPermission) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED_ADMIN_OR_HAS_PERMISSION, requiredPermission.name());
        }
    }

    /**
     * Deletes a document by its ID if the user has DELETE permission.
     *
     * @param documentId the ID of the document to delete
     * @param username   the username of the requester
     *
     * @throws ResourceNotFoundException if the document does not exist
=     */
    public void deleteDocument(final Long documentId, final String username) {

        Document doc = this.findById(documentId);
        checkPermission(doc, username, PermissionType.DELETE);
        documentRepo.delete(doc);
    }

    /**
     * Grants or updates permission for a user on a specific document.
     *
     * @param documentId the ID of the document to which the permission applies
     * @param username the username of the user performing the action (must be admin or have WRITE permission)
     * @param model the permission model containing the target user's username and the permission type
     * @throws AccessDeniedException if the user is not admin and does not have WRITE permission on the document
     */
    public void grantPermission(final Long documentId, final String username,
                                final DocumentPermissionModel model) {

        CreateDocumentPermissionValidation.validate(model, username);

        Document doc = this.findById(documentId);

        if (!ADMIN.equals(username)) {
            // Must have WRITE permission
            checkPermission(doc, username, PermissionType.WRITE);
        }

        // Add or update permission for target user
        DocumentPermission documentPermission =
                doc.getAccessibleUsers()
                .stream()
                .filter(p -> p.getUsername().equals(model.getUsername()))
                .findFirst()
                .orElse(null);

        if (documentPermission == null) {

            DocumentPermissionModel permissionModel =  new DocumentPermissionModel();

            permissionModel.setUsername(model.getUsername());
            permissionModel.setPermission(model.getPermission());
            permissionModel.setCreatedAt(LocalDateTime.now());
            doc.getAccessibleUsers().add(DocumentPermissionModelMapper.toEntity(permissionModel));
        }

        documentRepo.save(doc);
    }

    /**
     * Checks which documents the user has access to with a specific permission.
     *
     * @param username    the username of the requester
     * @param permission  the permission type to check (READ, WRITE, DELETE)
     * @param documentIds the list of document IDs to verify access for
     * @return a list of document IDs that the user has access to with the given permission
     */
    public List<Long> batchAccessCheck(final String username,
                                       final PermissionType permission,
                                       final List<Long> documentIds) {

        if (ADMIN.equals(username)) {

            return documentRepo.findByIds(documentIds)
              .stream()
              .map(Document::getId)
              .collect(Collectors.toList());
        }
        // Find documents where user has requested permission and id in documentIds
        List<Document> accessibleDocs = documentRepo.findByUsernameAndPermission(username, permission);
        Set<Long> accessibleIds = accessibleDocs.stream()
                .map(Document::getId)
                .collect(Collectors.toSet());

        return documentIds.stream()
                .filter(accessibleIds::contains)
                .toList();
    }

    /**
     * Finds a document by its ID.
     *
     * @param documentId the ID of the document to retrieve
     * @return the found {@link Document} entity
     * @throws ResourceNotFoundException if no document exists with the given ID
     */
    private Document findById(final Long documentId) {
        return documentRepo.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND));
    }

}