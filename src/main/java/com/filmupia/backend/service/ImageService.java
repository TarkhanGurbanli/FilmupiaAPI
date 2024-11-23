package com.filmupia.backend.service;

import com.filmupia.backend.entity.Image;
import com.filmupia.backend.entity.enums.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {
    Image uploadImage(MultipartFile file, String folderName, ImageType imageType);
}
