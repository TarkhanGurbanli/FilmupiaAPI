package com.filmupia.backend.service;

import com.filmupia.backend.model.image.ImageModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ImageService {
    ResponseEntity<Map> uploadImage(ImageModel imageModel);
}
