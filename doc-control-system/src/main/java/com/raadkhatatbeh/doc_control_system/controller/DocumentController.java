package com.raadkhatatbeh.doc_control_system.controller;

import com.raadkhatatbeh.doc_control_system.controller.path.ApiPaths;
import com.raadkhatatbeh.doc_control_system.dto.BatchAccessCheckRequest;
import com.raadkhatatbeh.doc_control_system.dto.BatchAccessCheckResponse;
import com.raadkhatatbeh.doc_control_system.model.DocumentModel;
import com.raadkhatatbeh.doc_control_system.model.DocumentPermissionModel;
import com.raadkhatatbeh.doc_control_system.repo.DocumentRepo;
import com.raadkhatatbeh.doc_control_system.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Document Controller.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
@RestController
@RequestMapping(ApiPaths.Document.DOCUMENTS)
public class DocumentController {

    private final DocumentService documentService;

    /**
     * Constructs a new {@code DocumentService} with the provided {@link DocumentRepo}.
     *
     * @param documentService the service used to manage document logic
     */
    public DocumentController(final DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Creates a new document.
     * This endpoint is restricted to the admin user only.
     * The created document is associated with the provided data in the request body.
     *
     * @param model the document model containing name, content, fileType, and accessibleUsers
     * @param username the username of the requester, passed via the X-User header
     *
     * @return the created document wrapped in a ResponseEntity
     */
    @Operation(
      summary = "Create a new document (Admin only)",
      description = "Creates a document based on the request body. Only the admin user is authorized.",
      responses = {
       @ApiResponse(responseCode = "200", description = "Document created successfully",
        content = @Content(schema = @Schema(implementation = DocumentModel.class))),
       @ApiResponse(responseCode = "400", description = "Invalid input or missing fields", content = @Content),
       @ApiResponse(responseCode = "403", description = "Access denied - Only admin allowed", content = @Content)
      })
    @PostMapping({"/", ""})
    public ResponseEntity<DocumentModel> createDocument(@RequestBody final DocumentModel model,
                                                        @RequestHeader("X-User") final String username) {
        return ResponseEntity.ok(documentService.createDocument(model, username));
    }

    /**
     * Retrieves a list of documents accessible by the specified user.
     *
     * @param username the username of the requester, passed via the X-User header
     * @return the list of accessible documents wrapped in a {@link ResponseEntity}
     */
    @Operation(
     summary = "get documents for a user",
     description = "Returns a list of documents accessible to the given user. Admins receive all documents," +
             " while regular users receive only documents they can READ.",
     responses = {
      @ApiResponse(responseCode = "200", description = "List of accessible documents",
       content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentModel.class)))),
      @ApiResponse(responseCode = "400", description = "Missing or invalid user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)
     })
    @GetMapping({"/", ""})
    public ResponseEntity<List<DocumentModel>> getDocuments(@RequestHeader("X-User") final String username) {
        return ResponseEntity.ok(documentService.getDocuments(username));
    }

    /**
     * Retrieves a specific document by its ID if the user has access.
     *
     * @param documentId the ID of the document to retrieve
     * @param username the username of the requester, passed via the X-User header
     * @return the document wrapped in a {@link ResponseEntity}
     */
    @Operation(
     summary = "Get a specific document by ID",
     description = "Returns a document if the requesting user is authorized to access it. Admin users can access" +
             " any document; others must have READ permission.",
     responses = {
      @ApiResponse(responseCode = "200", description = "Document retrieved successfully",
       content = @Content(schema = @Schema(implementation = DocumentModel.class))),
      @ApiResponse(responseCode = "403", description = "Access denied - User does not have permission", content = @Content),
      @ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
    })
    @GetMapping(ApiPaths.Document.GET_DOCUMENT)
    public ResponseEntity<DocumentModel> getDocument(@PathVariable final Long documentId,
                                                     @RequestHeader("X-User") final String username) {
        return ResponseEntity.ok(documentService.getDocument(documentId, username));
    }

    /**
     * Deletes a specific document by its ID if the user has DELETE permission.
     *
     * @param documentId the ID of the document to delete
     * @param username   the username of the requester, passed via the X-User header
     * @return an empty {@link ResponseEntity} with HTTP 204 No Content on success
     */
    @Operation(
     summary = "Delete a document by ID",
     description = "Deletes the specified document if the requesting user is authorized. " +
            "Admin users can delete any document; regular users must have DELETE permission.",
     responses = {
      @ApiResponse(responseCode = "204", description = "Document deleted successfully"),
      @ApiResponse(responseCode = "403", description = "Access denied - User does not have DELETE permission", content = @Content),
      @ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
    })
    @DeleteMapping(ApiPaths.Document.GET_DOCUMENT)
    public ResponseEntity<Void> deleteDocument(@PathVariable final Long documentId,
                                               @RequestHeader("X-User") final String username) {
        documentService.deleteDocument(documentId, username);
        return ResponseEntity.noContent().build();
    }

    /**
     * Grants a specific permission (e.g., READ, WRITE, DELETE) on a document to a target user.
     *
     * @param documentId        the ID of the document
     * @param username  the username of the requester (must be admin), passed via the X-User header
     * @param model   the {@link DocumentPermissionModel} containing the target username and permission type
     * @return an empty {@link ResponseEntity} with HTTP 200 OK on success
     */
    @Operation(
     summary = "Grant permission to a user for a document (Admin only)",
     description = "Allows the admin to assign a permission (READ, WRITE, DELETE) to another user for a specific document.",
     requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Permission details to assign",
        required = true,
        content = @Content(schema = @Schema(implementation = DocumentPermissionModel.class))),
    responses = {
      @ApiResponse(responseCode = "200", description = "Permission granted successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input or missing fields", content = @Content),
      @ApiResponse(responseCode = "403", description = "Access denied - Only admin allowed", content = @Content),
      @ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
    })
    @PostMapping(ApiPaths.Document.GRANT_PERMISSION)
    public ResponseEntity<Void> grantPermission(@PathVariable final Long documentId,
                                                @RequestHeader("X-User") final String username,
                                                @RequestBody DocumentPermissionModel model) {
        documentService.grantPermission(documentId, username, model);
        return ResponseEntity.ok().build();
    }

    /**
     * Checks which documents from a given list are accessible to the user
     * based on the requested permission type (e.g., READ, WRITE, DELETE).
     *
     * @param username the username of the requester, passed via the X-User header
     * @param request  the batch access check request containing document IDs and the permission type
     * @return a {@link ResponseEntity} containing a list of accessible document IDs
     */
    @Operation(
     summary = "Batch access check for documents", description = "Checks which document IDs are accessible to the requesting user based on the provided permission type.",
     requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
        description = "List of document IDs and the permission to check", content = @Content(schema = @Schema(implementation = BatchAccessCheckRequest.class))
    ),
    responses = {
     @ApiResponse(responseCode = "200", description = "List of accessible document IDs",
      content = @Content(schema = @Schema(implementation = BatchAccessCheckResponse.class))),
     @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
     @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)
    }
)
    @PostMapping(ApiPaths.Document.ACCESS_CHECK)
    public ResponseEntity<BatchAccessCheckResponse> batchAccessCheck(@RequestHeader("X-User") final String username,
                                                                     @RequestBody final BatchAccessCheckRequest request) {

        List<Long> accessibleIds = documentService.batchAccessCheck(username, request.getPermission(), request.getDocumentIds());
        return ResponseEntity.ok(new BatchAccessCheckResponse(accessibleIds));
    }
}
