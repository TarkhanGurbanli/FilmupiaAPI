package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "actors")
public class Actor extends BaseEntity {

    @NotBlank(message = "Full name cannot be empty.")
    @Size(max = 100, message = "Full name cannot exceed 100 characters.")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotBlank(message = "Image URL cannot be empty.")
    @Size(max = 255, message = "Image URL cannot exceed 255 characters.")
    private String imageUrl;
}
