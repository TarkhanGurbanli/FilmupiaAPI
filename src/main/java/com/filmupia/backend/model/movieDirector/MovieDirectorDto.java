package com.filmupia.backend.model.movieDirector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDirectorDto {
    private Long id;
    private Long movieId;
    private Long directorId;
}
