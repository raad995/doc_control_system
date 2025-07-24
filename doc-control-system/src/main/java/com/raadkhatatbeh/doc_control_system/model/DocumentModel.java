package com.raadkhatatbeh.doc_control_system.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DocumentModel Model.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class DocumentModel {

    private Long id;
    private String name;
    private String content;
    private FileType fileType;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime UpdatedAt;
    private List<DocumentPermissionModel> accessibleUsers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(final FileType fileType) {
        this.fileType = fileType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        UpdatedAt = updatedAt;
    }

    public List<DocumentPermissionModel> getAccessibleUsers() {
        return accessibleUsers;
    }

    public void setAccessibleUsers(final List<DocumentPermissionModel> accessibleUsers) {
        this.accessibleUsers = accessibleUsers;
    }
}
