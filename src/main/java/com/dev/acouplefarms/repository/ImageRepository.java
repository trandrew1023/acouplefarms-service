package com.dev.acouplefarms.repository;

import com.dev.acouplefarms.models.images.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {}
