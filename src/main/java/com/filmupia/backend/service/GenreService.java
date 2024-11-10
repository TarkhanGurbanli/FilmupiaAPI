package com.filmupia.backend.service;

import com.filmupia.backend.model.genre.CreateGenreDto;
import com.filmupia.backend.model.genre.GenreDto;
import com.filmupia.backend.model.genre.UpdateGenreDto;

import java.util.List;

public interface GenreService {
    CreateGenreDto createGenre(CreateGenreDto createGenreDto);
    void updateGenre(Long genreId, UpdateGenreDto updateGenreDto);
    void deleteGenre(Long genreId);
    GenreDto getGenre(Long genreId);
    List<GenreDto> getGenres();
    List<GenreDto> getGenresWithPagination(int pageNumber, int pageSize);
}
