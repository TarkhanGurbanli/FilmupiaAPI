package com.filmupia.backend.service;

import com.filmupia.backend.model.movie.CreateMovieDto;
import com.filmupia.backend.model.movie.MovieDto;
import com.filmupia.backend.model.movie.UpdateMovieDto;

import java.util.List;

public interface MovieService {
    CreateMovieDto createMovie(CreateMovieDto createMovieDto);
    void updateMovie(Long movieId, UpdateMovieDto updateMovieDto);
    void deleteMovie(Long movieId);
    MovieDto getMovie(Long movieId);
    List<MovieDto> getMovies();
    List<MovieDto> getMoviesWithPagination(int pageNumber, int pageSize);
}
