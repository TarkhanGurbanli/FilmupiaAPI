package com.filmupia.backend.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@Builder
public class UploadImageDto {
    private MultipartFile image;
}
