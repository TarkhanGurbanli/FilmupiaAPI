package com.filmupia.backend.model.watchlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistDto {
    private Long id;
    private Long movieId;
    private String movieName;
    private int publicationYear;
    private Double rating;
    private String imageUrl;
}
