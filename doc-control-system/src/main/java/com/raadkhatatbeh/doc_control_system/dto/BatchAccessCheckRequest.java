package com.raadkhatatbeh.doc_control_system.dto;

import com.raadkhatatbeh.doc_control_system.model.PermissionType;
import java.util.List;

/**
 * BatchAccessCheckRequest dto.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class BatchAccessCheckRequest {

    private PermissionType permission;
    private List<Long> documentIds;

    public PermissionType getPermission() {
        return permission;
    }

    public void setPermission(final PermissionType permission) {
        this.permission = permission;
    }

    public List<Long> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(final List<Long> documentIds) {
        this.documentIds = documentIds;
    }
}
