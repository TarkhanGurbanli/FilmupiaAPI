package com.filmupia.backend.repository;

import com.filmupia.backend.entity.Image;
import com.filmupia.backend.entity.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByImageType(ImageType imageType);
}
