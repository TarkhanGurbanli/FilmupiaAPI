package com.filmupia.backend.service;

import com.filmupia.backend.model.movieDirector.CreateMovieDirectorDto;
import com.filmupia.backend.model.movieDirector.MovieDirectorDto;
import com.filmupia.backend.model.movieDirector.UpdateMovieDirectorDto;

import java.util.List;

public interface MovieDirectorService {
    CreateMovieDirectorDto createMovieDirector(CreateMovieDirectorDto createMovieDirectorDto);
    void updateMovieDirector(Long movieDirectorId, UpdateMovieDirectorDto updateMovieDirectorDto);
    void deleteMovieDirector(Long movieDirectorId);
    MovieDirectorDto getMovieDirector(Long movieDirectorId);
    List<MovieDirectorDto> getMovieDirectors();
    List<MovieDirectorDto> getMovieDirectorsWithPagination(int pageNumber, int pageSize);
}
