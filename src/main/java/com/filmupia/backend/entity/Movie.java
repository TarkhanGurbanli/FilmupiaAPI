package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private String name;
    private String description;
    private Double rating;
    private String imageUrl;
    private String trailerUrl;
    private int publicationYear;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<MovieGenre> movieGenres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<MovieDirector> movieDirectors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<MovieActor> movieActors;
}
