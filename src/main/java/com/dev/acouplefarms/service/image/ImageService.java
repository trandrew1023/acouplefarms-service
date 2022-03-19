package com.dev.acouplefarms.service.image;

import com.dev.acouplefarms.models.images.Image;

public interface ImageService {
  void saveProfileImage(final Image image, final Long userId);

  Image getProfileImage(final Long userId);

  Image saveImage(final Image image);
}
