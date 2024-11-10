package com.filmupia.backend.model.movieActor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieActorDto {
    private Long movieId;
    private Long actorId;
    private String characterName;
}
