package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Movie;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.movie.CreateMovieDto;
import com.filmupia.backend.model.movie.MovieDto;
import com.filmupia.backend.model.movie.UpdateMovieDto;
import com.filmupia.backend.repository.MovieRepository;
import com.filmupia.backend.service.MovieService;
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
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateMovieDto createMovie(CreateMovieDto createMovieDto) {
        Movie movie = modelMapper.map(createMovieDto, Movie.class);
        return modelMapper.map(movieRepository.save(movie), CreateMovieDto.class);
    }

    @Override
    public void updateMovie(Long movieId, UpdateMovieDto updateMovieDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", movieId));

        modelMapper.map(updateMovieDto, movie);

        if(movie.getName().isEmpty())
            throw new FilmupiaApiException("Movie name cannot be empty");

        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", movieId));
        movieRepository.delete(movie);
    }

    @Override
    public MovieDto getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", movieId));

        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<MovieDto> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getMoviesWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Movie> movies = movieRepository.findAll(pageable).getContent();
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }
}
