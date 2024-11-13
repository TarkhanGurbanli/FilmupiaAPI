package com.filmupia.backend.model.movieGenre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieGenreDto {
    private Long movieId;
    private Long genreId;
}
