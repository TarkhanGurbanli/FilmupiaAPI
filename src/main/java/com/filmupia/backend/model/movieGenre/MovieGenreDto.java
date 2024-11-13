package com.filmupia.backend.model.movieGenre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenreDto {
    private Long id;
    private Long movieId;
    private Long genreId;
}
