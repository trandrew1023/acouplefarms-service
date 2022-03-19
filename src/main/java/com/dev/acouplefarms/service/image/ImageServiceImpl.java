package com.dev.acouplefarms.service.image;

import com.dev.acouplefarms.models.images.Image;
import com.dev.acouplefarms.models.images.ProfileImage;
import com.dev.acouplefarms.repository.ImageRepository;
import com.dev.acouplefarms.repository.ProfileImageRepository;
import java.sql.Date;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;
  private final ProfileImageRepository profileImageRepository;

  @Override
  public void saveProfileImage(final Image image, final Long userId) {
    final Image savedImage = imageRepository.save(image);
    profileImageRepository.save(
        new ProfileImage(
            userId, savedImage.getId(), Date.from(Instant.now()), Date.from(Instant.now())));
  }

  @Override
  public Image getProfileImage(final Long userId) {
    final Optional<ProfileImage> profileImage = profileImageRepository.findById(userId);
    if (profileImage.isEmpty()) {
      return null;
    }
    return imageRepository.getById(profileImage.get().getImageId());
  }

  @Override
  public Image saveImage(final Image image) {
    return imageRepository.save(image);
  }
}
