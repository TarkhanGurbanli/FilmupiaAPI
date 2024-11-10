package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "directors")
public class Director extends BaseEntity {

    @NotBlank(message = "Director name cannot be empty")
    @Size(min = 2, max = 100, message = "Director name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 255, message = "Image URL must be less than 255 characters")
    private String imageUrl;
}
