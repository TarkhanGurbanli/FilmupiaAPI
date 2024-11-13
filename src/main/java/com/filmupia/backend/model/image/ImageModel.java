package com.filmupia.backend.model.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ImageModel {
    private String name;
    private MultipartFile file;
}
