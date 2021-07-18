package com.sastore.web.uploader.domain;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public enum ImageFolder {

    PRODUCTS("products"),
    USERS("users");

    private final String folder;

    private ImageFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
