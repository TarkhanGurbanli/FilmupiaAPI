package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Genre;
import com.filmupia.backend.entity.Movie;
import com.filmupia.backend.entity.MovieGenre;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.movieGenre.CreateMovieGenreDto;
import com.filmupia.backend.model.movieGenre.MovieGenreDto;
import com.filmupia.backend.model.movieGenre.UpdateMovieGenreDto;
import com.filmupia.backend.repository.GenreRepository;
import com.filmupia.backend.repository.MovieGenreRepository;
import com.filmupia.backend.repository.MovieRepository;
import com.filmupia.backend.service.MovieGenreService;
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
public class MovieGenreServiceImpl implements MovieGenreService {

    private final MovieGenreRepository movieGenreRepository;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateMovieGenreDto createMovieGenre(CreateMovieGenreDto createMovieGenreDto) {
        MovieGenre movieGenre = new MovieGenre();

        if (createMovieGenreDto.getGenreId() == null || createMovieGenreDto.getMovieId() == null) {
            throw new FilmupiaApiException("Genre and Movie must not be null");
        }

        Genre genre = genreRepository.findById(createMovieGenreDto.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", createMovieGenreDto.getGenreId()));

        Movie movie = movieRepository.findById(createMovieGenreDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", createMovieGenreDto.getMovieId()));

        movieGenre.setGenre(genre);
        movieGenre.setMovie(movie);

        movieGenre = movieGenreRepository.save(movieGenre);

        CreateMovieGenreDto responseDto = new CreateMovieGenreDto();
        responseDto.setGenreId(movieGenre.getGenre().getId());
        responseDto.setMovieId(movieGenre.getMovie().getId());
        return responseDto;
    }

    @Override
    public void updateMovieGenre(Long movieGenreId, UpdateMovieGenreDto updateMovieGenreDto) {
        MovieGenre movieGenre = movieGenreRepository.findById(movieGenreId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieGenre", "id", movieGenreId));

        if (updateMovieGenreDto.getGenreId() == null || updateMovieGenreDto.getMovieId() == null) {
            throw new FilmupiaApiException("Genre and Movie must not be null");
        }

        Genre genre = genreRepository.findById(updateMovieGenreDto.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", updateMovieGenreDto.getGenreId()));

        Movie movie = movieRepository.findById(updateMovieGenreDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", updateMovieGenreDto.getMovieId()));

        movieGenre.setGenre(genre);
        movieGenre.setMovie(movie);
        movieGenreRepository.save(movieGenre);
    }

    @Override
    public void deleteMovieGenre(Long movieGenreId) {
        MovieGenre movieGenre = movieGenreRepository.findById(movieGenreId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieGenre", "id", movieGenreId));
        movieGenreRepository.delete(movieGenre);
    }

    @Override
    public MovieGenreDto getMovieGenre(Long movieGenreId) {
        MovieGenre movieGenre = movieGenreRepository.findById(movieGenreId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieGenre", "id", movieGenreId));

        return modelMapper.map(movieGenre, MovieGenreDto.class);
    }

    @Override
    public List<MovieGenreDto> getMovieGenres() {
        List<MovieGenre> movieGenres = movieGenreRepository.findAll();
        return movieGenres.stream()
                .map(movieGenre -> modelMapper.map(movieGenre, MovieGenreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieGenreDto> getMovieGenresWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<MovieGenre> movieGenres = movieGenreRepository.findAll(pageable).getContent();
        return movieGenres.stream()
                .map(movieGenre -> modelMapper.map(movieGenre, MovieGenreDto.class))
                .collect(Collectors.toList());
    }
}
