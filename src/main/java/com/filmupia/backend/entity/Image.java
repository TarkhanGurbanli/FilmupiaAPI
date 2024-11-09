package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import com.filmupia.backend.entity.enums.ImageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String imageUrl;

    private int status;
    private String message;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    private LocalDateTime uploadedAt;
}
