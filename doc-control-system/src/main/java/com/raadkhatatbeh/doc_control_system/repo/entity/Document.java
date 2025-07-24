package com.raadkhatatbeh.doc_control_system.repo.entity;

import com.raadkhatatbeh.doc_control_system.model.FileType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Document Entity.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
@Entity
@Table(name = "DOCUMENT")
public class Document {

    private Long id;
    private String name;
    private String content;
    private FileType fileType;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<DocumentPermission> accessibleUsers = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "FILE_TYPE")
    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(final FileType fileType) {
        this.fileType = fileType;
    }

    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_AT")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "UPDATED_AT")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "ID")
    public List<DocumentPermission> getAccessibleUsers() {
        return accessibleUsers;
    }

    public void setAccessibleUsers(final List<DocumentPermission> accessibleUsers) {
        this.accessibleUsers = accessibleUsers;
    }

}
