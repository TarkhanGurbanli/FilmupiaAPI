package com.filmupia.backend.service;

import com.filmupia.backend.model.movieActor.CreateMovieActorDto;
import com.filmupia.backend.model.movieActor.MovieActorDto;
import com.filmupia.backend.model.movieActor.UpdateMovieActorDto;

import java.util.List;

public interface MovieActorService {
    CreateMovieActorDto createMovieActor(CreateMovieActorDto createMovieActorDto);
    void updateMovieActor(Long movieActorId, UpdateMovieActorDto updateMovieActorDto);
    void deleteMovieActor(Long movieActorId);
    MovieActorDto getMovieActor(Long movieActorId);
    List<MovieActorDto> getMovieActors();
    List<MovieActorDto> getMovieActorsWithPagination(int pageNumber, int pageSize);
}
