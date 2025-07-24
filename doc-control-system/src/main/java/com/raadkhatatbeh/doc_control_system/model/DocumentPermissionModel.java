package com.raadkhatatbeh.doc_control_system.model;

import java.time.LocalDateTime;

/**
 * DocumentPermissionModel Model.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class DocumentPermissionModel {

    private Long id;
    private String username;
    private PermissionType permission;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public PermissionType getPermission() {
        return permission;
    }

    public void setPermission(final PermissionType permission) {
        this.permission = permission;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
