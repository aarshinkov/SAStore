package com.sastore.web.enums;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public enum ProductStatuses {
    ACTIVE(0),
    PENDING_APPROVAL(1),
    INACTIVE(2),
    DELETED(3);

    private final Integer status;

    private ProductStatuses(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
