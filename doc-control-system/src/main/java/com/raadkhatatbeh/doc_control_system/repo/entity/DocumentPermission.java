package com.raadkhatatbeh.doc_control_system.repo.entity;

import com.raadkhatatbeh.doc_control_system.model.PermissionType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * DocumentPermission Entity.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
@Entity
@Table(name = "DOCUMENT_PERMISSION")
public class DocumentPermission {

    private Long id;
    private String username;
    private PermissionType permissionType;
    private LocalDateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "PERMISSION_TYPE")
    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(final PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    @Column(name = "CREATED_AT")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
