package com.sastore.web.uploader.domain;

import java.io.File;
import java.util.UUID;

/**
 * Used for file uploading. Contains the primary information about the file such
 * as type, path and size.
 *
 * @author Atanas Yordanov Arshinkov
 * @see java.util.UUID
 * @since 1.0.0
 */
public class FileUpload {

    private File file;
    private UUID id;
    private Type type;
    private Double size;

    /**
     * Creates the file object by getting the absolute path to it.
     *
     * @param path the absolute path to the file on the file system
     * @see Type
     * @see java.io.File
     */
    public FileUpload(String path) {
        super();
        this.id = UUID.randomUUID();

        this.file = new File(path);
        setType();
        setSize();
    }

    private void setType() {
        String[] split = this.file.getName().split("\\.");
        String type = split[split.length - 1].toLowerCase();

        switch (type) {
            case "jpg":
            case "jpeg":
                this.type = Type.JPG;
                break;
            case "png":
                this.type = Type.PNG;
                break;
            case "txt":
                this.type = Type.TXT;
                break;
            case "xml":
                this.type = Type.XML;
                break;
            case "pdf":
                this.type = Type.PDF;
                break;
            case "zip":
                this.type = Type.ZIP;
                break;
            case "rar":
                this.type = Type.RAR;
                break;
            case "json":
                this.type = Type.JSON;
                break;
        }
    }

    private void setSize() {
        this.size = (double) file.length() / (Math.pow(1024, 2)); // In MegaBytes
    }

    public File getFile() {
        return file;
    }

    public UUID getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getExtension() {
        return "." + type.toString().toLowerCase();
    }

    public Double getSize() {
        return size;
    }
}
