package com.filmupia.backend.service;

import com.filmupia.backend.model.movieGenre.CreateMovieGenreDto;
import com.filmupia.backend.model.movieGenre.MovieGenreDto;
import com.filmupia.backend.model.movieGenre.UpdateMovieGenreDto;

import java.util.List;

public interface MovieGenreService {
    CreateMovieGenreDto createMovieGenre(CreateMovieGenreDto createMovieGenreDto);
    void updateMovieGenre(Long movieGenreId, UpdateMovieGenreDto updateMovieGenreDto);
    void deleteMovieGenre(Long movieGenreId);
    MovieGenreDto getMovieGenre(Long movieGenreId);
    List<MovieGenreDto> getMovieGenres();
    List<MovieGenreDto> getMovieGenresWithPagination(int pageNumber, int pageSize);
}
