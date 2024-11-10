package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blogs")
public class Blog extends BaseEntity {

    @NotBlank(message = "Title cannot be empty.")
    @Size(max = 200, message = "Title cannot exceed 200 characters.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Content cannot be empty.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
