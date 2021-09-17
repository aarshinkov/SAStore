package com.sastore.web.enums;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public enum Order {
    
    ASCENDING("ASC"),
    DESCENDING("DESC");

    private final String order;

    Order(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
