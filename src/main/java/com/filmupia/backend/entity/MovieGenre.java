package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_genres")
public class MovieGenre extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @NotNull(message = "Movie cannot be null")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    @NotNull(message = "Genre cannot be null")
    private Genre genre;
}
