package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Actor;
import com.filmupia.backend.entity.Movie;
import com.filmupia.backend.entity.MovieActor;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.movieActor.CreateMovieActorDto;
import com.filmupia.backend.model.movieActor.MovieActorDto;
import com.filmupia.backend.model.movieActor.UpdateMovieActorDto;
import com.filmupia.backend.repository.ActorRepository;
import com.filmupia.backend.repository.MovieActorRepository;
import com.filmupia.backend.repository.MovieRepository;
import com.filmupia.backend.service.MovieActorService;
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
public class MovieActorServiceImpl implements MovieActorService {

    private final MovieActorRepository movieActorRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateMovieActorDto createMovieActor(CreateMovieActorDto createMovieActorDto) {
        MovieActor movieActor = new MovieActor();

        if (createMovieActorDto.getActorId() == null || createMovieActorDto.getMovieId() == null) {
            throw new FilmupiaApiException("Actor and Movie must not be null");
        }

        Actor actor = actorRepository.findById(createMovieActorDto.getActorId()).orElseThrow(
                () -> new ResourceNotFoundException("Actor", "id", createMovieActorDto.getActorId()));
        Movie movie = movieRepository.findById(createMovieActorDto.getMovieId()).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", createMovieActorDto.getMovieId()));

        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        movieActor.setCharacterName(createMovieActorDto.getCharacterName());

        movieActor = movieActorRepository.save(movieActor);

        CreateMovieActorDto responseDto = new CreateMovieActorDto();
        responseDto.setActorId(movieActor.getActor().getId());
        responseDto.setMovieId(movieActor.getMovie().getId());
        responseDto.setCharacterName(movieActor.getCharacterName());

        return responseDto;
    }

    @Override
    public void updateMovieActor(Long movieActorId, UpdateMovieActorDto updateMovieActorDto) {
        MovieActor movieActor = movieActorRepository.findById(movieActorId).orElseThrow(
                () -> new ResourceNotFoundException("MovieActor", "id", movieActorId));

        if (updateMovieActorDto.getActorId() == null || updateMovieActorDto.getMovieId() == null) {
            throw new FilmupiaApiException("Actor and Movie must not be null");
        }

        Actor actor = actorRepository.findById(updateMovieActorDto.getActorId()).orElseThrow(
                () -> new ResourceNotFoundException("Actor", "id", updateMovieActorDto.getActorId()));
        Movie movie = movieRepository.findById(updateMovieActorDto.getMovieId()).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", updateMovieActorDto.getMovieId()));

        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        movieActor.setCharacterName(updateMovieActorDto.getCharacterName());

        movieActorRepository.save(movieActor);
    }

    @Override
    public void deleteMovieActor(Long movieActorId) {
        MovieActor movieActor = movieActorRepository.findById(movieActorId).orElseThrow(
                () -> new ResourceNotFoundException("MovieActor", "id", movieActorId));
        movieActorRepository.delete(movieActor);
    }

    @Override
    public MovieActorDto getMovieActor(Long movieActorId) {
        MovieActor movieActor = movieActorRepository.findById(movieActorId).orElseThrow(
                () -> new ResourceNotFoundException("MovieActor", "id", movieActorId));

        return modelMapper.map(movieActor, MovieActorDto.class);
    }

    @Override
    public List<MovieActorDto> getMovieActors() {
        List<MovieActor> movieActors = movieActorRepository.findAll();
        return movieActors.stream()
                .map(movieActor -> modelMapper.map(movieActor, MovieActorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieActorDto> getMovieActorsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<MovieActor> movieActors = movieActorRepository.findAll(pageable).getContent();
        return movieActors.stream()
                .map(movieActor -> modelMapper.map(movieActor, MovieActorDto.class))
                .collect(Collectors.toList());
    }
}
