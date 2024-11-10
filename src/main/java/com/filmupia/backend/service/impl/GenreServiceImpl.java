package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Genre;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.genre.CreateGenreDto;
import com.filmupia.backend.model.genre.GenreDto;
import com.filmupia.backend.model.genre.UpdateGenreDto;
import com.filmupia.backend.repository.GenreRepository;
import com.filmupia.backend.service.GenreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateGenreDto createGenre(CreateGenreDto createGenreDto) {
        Genre genre = modelMapper.map(createGenreDto, Genre.class);
        boolean exists = genreRepository.existsByName(createGenreDto.getName());

        if (exists)
            throw new FilmupiaApiException("Genre name " + genre.getName() + " already exists");

        return modelMapper.map(genreRepository.save(genre), CreateGenreDto.class);
    }

    @Override
    public void updateGenre(Long genreId, UpdateGenreDto updateGenreDto) {
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException("Genre", "id", genreId));

        modelMapper.map(updateGenreDto, genre);
        genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException("Genre", "id", genreId));
        genreRepository.delete(genre);
    }

    @Override
    public GenreDto getGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException("Genre", "id", genreId));

        return modelMapper.map(genre, GenreDto.class);
    }

    @Override
    public List<GenreDto> getGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreDto> getGenresWithPagination(int pageNumber, int pageSize) {
        if (pageNumber < 1)
            throw new FilmupiaApiException("Page number should be greater than 0");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Genre> genres = genreRepository.findAll(pageable).getContent();
        return genres.stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .collect(Collectors.toList());
    }
}
