package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
public class Movie extends BaseEntity {

    @NotBlank(message = "Movie name cannot be empty")
    @Size(min = 1, max = 150, message = "Movie name must be between 1 and 150 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating cannot be less than 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Rating cannot be greater than 10.0")
    private Double rating;

    @Size(max = 255, message = "Image URL must be less than 255 characters")
    private String imageUrl;

    @Size(max = 255, message = "Trailer URL must be less than 255 characters")
    private String trailerUrl;

    @Min(value = 1800, message = "Publication year must be after 1800")
    @Max(value = 2100, message = "Publication year must be before 2100")
    private int publicationYear;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieGenre> movieGenres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieDirector> movieDirectors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieActor> movieActors;
}
