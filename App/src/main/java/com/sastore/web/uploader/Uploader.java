package com.sastore.web.uploader;

import com.sastore.web.uploader.domain.FileName;
import com.sastore.web.uploader.domain.FileUpload;
import com.sastore.web.uploader.domain.ImageFolder;
import java.io.*;
import java.util.*;
import org.apache.commons.io.*;

/**
 * The class is responsible for operations with files.
 *
 * @author Atanas Yordanov Arshinkov
 */
public class Uploader {

    private final String IMAGES_DIR = File.separator;

    /**
     * Uploads a given image in byte array.It receives the original name of the
     * file, containing the extension. The method uploads the file to the web
     * server and returns the newly created name, generated automatically.
     *
     * @param fileBytes the image in byte array
     * @param originalFileName the name of the original file, containing the
     * extension
     * @param folder the folder to which the image should be uploaded
     * @return the uploaded file's name which is automatically generated
     * @throws IOException if there's an error while uploading file
     */
    public FileName uploadFile(byte[] fileBytes, String originalFileName, ImageFolder folder) throws IOException {

        try {
            // Creating the directory to store file
//            String rootPath = System.getProperty("catalina.home");
            String rootPath = "A:\\images";
            File dir = new File(rootPath + IMAGES_DIR + folder.getFolder());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            FileName fileName = new FileName();

            String name = String.valueOf(UUID.randomUUID());
            String extension = FilenameUtils.getExtension(originalFileName);

            fileName.setFileName(name);
            fileName.setExtension(extension);

            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName.getFullName());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(fileBytes);
            stream.close();

            return fileName;
        } catch (IOException e) {
            throw new IOException("File failed to upload");
        }
    }

    /**
     * Gets a file from the file system and copies it to a destination folder
     * with an unique name generated automatically.
     *
     * @param file the file to be copied
     * @param destination the path the file to be copied to
     * @throws IOException if the file is not found
     */
    public void copyFileToDirectory(FileUpload file, String destination) throws IOException {

        File src = file.getFile();

        destination = destination + file.getId() + file.getExtension();

        File dest = new File(destination);

        FileUtils.copyFile(src, dest);
    }

    /**
     * Uploads a given image in byte array. It receives the original name of the
     * file, containing the extension. The method uploads the file to the web
     * server and returns the newly created name, generated automatically.
     * <br><br>
     * Use <code>uploadFile(byte[] fileBytes, String originalFileName)</code>
     * instead.
     *
     * @param fileBytes the image in byte array
     * @param originalFileName the name of the original file, containing the
     * extension
     * @param withExtension true - returns the file name with extension, false -
     * only the name
     * @return the uploaded file's name which is automatically generated
     * @throws IOException if there's an error while uploading file
     */
    @Deprecated
    public String uploadFile(byte[] fileBytes, String originalFileName, boolean withExtension) throws IOException {

        try {
            // Creating the directory to store file
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + IMAGES_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String extension = FilenameUtils.getExtension(originalFileName);

            String name = String.valueOf(UUID.randomUUID());
            String wholeName = name + "." + extension;

            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + wholeName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(fileBytes);
            stream.close();

            return withExtension ? wholeName : name;
        } catch (IOException e) {
            throw new IOException("File failed to upload");
        }
    }
}
