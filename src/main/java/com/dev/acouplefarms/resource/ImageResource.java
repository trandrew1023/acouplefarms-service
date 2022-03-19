package com.dev.acouplefarms.resource;

import com.dev.acouplefarms.models.images.Image;
import com.dev.acouplefarms.models.images.SaveImageCriteria;
import com.dev.acouplefarms.models.user.User;
import com.dev.acouplefarms.service.image.ImageService;
import com.dev.acouplefarms.service.user.UserService;
import java.sql.Date;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/image")
public class ImageResource {
  @Autowired private final ImageService imageService;
  @Autowired private final UserService userService;

  public ImageResource(final ImageService imageService, final UserService userService) {
    this.imageService = imageService;
    this.userService = userService;
  }

  @GetMapping("/profile")
  public ResponseEntity<?> getProfileImage(final HttpServletRequest request) {
    final User user = userService.getUserByUsername(request.getUserPrincipal().getName());
    final Image image = imageService.getProfileImage(user.getId());
    if (image == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(image);
  }

  @PostMapping("/profile")
  public ResponseEntity<?> saveProfileImage(
      @RequestBody final SaveImageCriteria saveImageCriteria, final HttpServletRequest request) {
    final User user = userService.getUserByUsername(request.getUserPrincipal().getName());
    final Image image = imageService.getProfileImage(user.getId());
    log.info(saveImageCriteria.getUrl());
    if (image == null) {
      log.info("new image");
      imageService.saveProfileImage(
          new Image(
              null, saveImageCriteria.getUrl(), Date.from(Instant.now()), Date.from(Instant.now())),
          user.getId());
    } else {
      log.info("old image");
      image.setUrl(saveImageCriteria.getUrl());
      image.setUpdateDate(Date.from(Instant.now()));
      imageService.saveImage(image);
    }
    return ResponseEntity.noContent().build();
  }
}
