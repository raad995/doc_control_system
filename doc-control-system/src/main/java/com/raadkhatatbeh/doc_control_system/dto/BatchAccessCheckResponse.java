package com.raadkhatatbeh.doc_control_system.dto;

import java.util.List;

/**
 * BatchAccessCheckResponse dto.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class BatchAccessCheckResponse {

    private List<Long> accessibleIds;

    public BatchAccessCheckResponse(final List<Long> accessibleIds) {
        this.accessibleIds = accessibleIds;
    }

    public List<Long> getAccessibleIds() {
        return accessibleIds;
    }

    public void setAccessibleIds(final List<Long> accessibleIds) {
        this.accessibleIds = accessibleIds;
    }
}
