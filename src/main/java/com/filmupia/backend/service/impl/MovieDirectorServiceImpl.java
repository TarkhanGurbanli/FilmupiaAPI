package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Director;
import com.filmupia.backend.entity.Movie;
import com.filmupia.backend.entity.MovieDirector;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.movieDirector.CreateMovieDirectorDto;
import com.filmupia.backend.model.movieDirector.MovieDirectorDto;
import com.filmupia.backend.model.movieDirector.UpdateMovieDirectorDto;
import com.filmupia.backend.repository.DirectorRepository;
import com.filmupia.backend.repository.MovieDirectorRepository;
import com.filmupia.backend.repository.MovieRepository;
import com.filmupia.backend.service.MovieDirectorService;
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
public class MovieDirectorServiceImpl implements MovieDirectorService {

    private final MovieDirectorRepository movieDirectorRepository;
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateMovieDirectorDto createMovieDirector(CreateMovieDirectorDto createMovieDirectorDto) {
        MovieDirector movieDirector = new MovieDirector();

        if (createMovieDirectorDto.getDirectorId() == null || createMovieDirectorDto.getMovieId() == null) {
            throw new FilmupiaApiException("Director and Movie must not be null");
        }

        Movie movie = movieRepository.findById(createMovieDirectorDto.getMovieId()).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", createMovieDirectorDto.getMovieId()));

        Director director = directorRepository.findById(createMovieDirectorDto.getDirectorId()).orElseThrow(
                () -> new ResourceNotFoundException("Director", "id", createMovieDirectorDto.getDirectorId()));

        movieDirector.setDirector(director);
        movieDirector.setMovie(movie);

        movieDirector = movieDirectorRepository.save(movieDirector);

        CreateMovieDirectorDto responseDto = new CreateMovieDirectorDto();
        responseDto.setDirectorId(movieDirector.getDirector().getId());
        responseDto.setMovieId(movieDirector.getMovie().getId());
        return responseDto;
    }

    @Override
    public void updateMovieDirector(Long movieDirectorId, UpdateMovieDirectorDto updateMovieDirectorDto) {
        MovieDirector movieDirector = movieDirectorRepository.findById(movieDirectorId).orElseThrow(
                () -> new ResourceNotFoundException("MovieDirector", "id", movieDirectorId));

        if (updateMovieDirectorDto.getDirectorId() == null || updateMovieDirectorDto.getMovieId() == null) {
            throw new FilmupiaApiException("Director and Movie must not be null");
        }

        Movie movie = movieRepository.findById(updateMovieDirectorDto.getMovieId()).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", updateMovieDirectorDto.getMovieId()));

        Director director = directorRepository.findById(updateMovieDirectorDto.getDirectorId()).orElseThrow(
                () -> new ResourceNotFoundException("Director", "id", updateMovieDirectorDto.getDirectorId()));

        movieDirector.setDirector(director);
        movieDirector.setMovie(movie);
        movieDirectorRepository.save(movieDirector);
    }

    @Override
    public void deleteMovieDirector(Long movieDirectorId) {
        MovieDirector movieDirector = movieDirectorRepository.findById(movieDirectorId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieDirector", "id", movieDirectorId));
        movieDirectorRepository.delete(movieDirector);
    }

    @Override
    public MovieDirectorDto getMovieDirector(Long movieDirectorId) {
        MovieDirector movieDirector = movieDirectorRepository.findById(movieDirectorId)
                .orElseThrow(() -> new ResourceNotFoundException("MovieDirector", "id", movieDirectorId));

        return modelMapper.map(movieDirector, MovieDirectorDto.class);
    }

    @Override
    public List<MovieDirectorDto> getMovieDirectors() {
        List<MovieDirector> movieDirectors = movieDirectorRepository.findAll();
        return movieDirectors.stream()
                .map(movieDirector -> modelMapper.map(movieDirector, MovieDirectorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDirectorDto> getMovieDirectorsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<MovieDirector> movieDirectors = movieDirectorRepository.findAll(pageable).getContent();
        return movieDirectors.stream()
                .map(movieDirector -> modelMapper.map(movieDirector, MovieDirectorDto.class))
                .collect(Collectors.toList());
    }
}
