package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.uploader.Uploader;
import com.sastore.web.uploader.domain.FileName;
import com.sastore.web.uploader.domain.ImageFolder;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class TestController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private Uploader uploader;

  @GetMapping("/test/image")
  public String testImage(Model model) {
    return "test/image";
  }

  @PostMapping("/test/image")
  public String testUploadImage(@RequestParam("file") MultipartFile file) {

    log.debug("Uploading image");

    FileName name = null;

    try {
      name = uploader.uploadFile(file.getBytes(), file.getOriginalFilename(), ImageFolder.USERS);

    } catch (IOException e) {
      log.error("Error uploading a file!", e);
    }

    return "redirect:/test/image";
  }
}
