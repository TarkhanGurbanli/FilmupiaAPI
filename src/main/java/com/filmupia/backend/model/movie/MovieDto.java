package com.filmupia.backend.model.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String imageUrl;
    private String trailerUrl;
    private int publicationYear;
}
