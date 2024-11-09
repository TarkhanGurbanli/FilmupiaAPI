package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Director;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.director.CreateDirectorDto;
import com.filmupia.backend.model.director.DirectorDto;
import com.filmupia.backend.model.director.UpdateDirectorDto;
import com.filmupia.backend.repository.DirectorRepository;
import com.filmupia.backend.service.DirectorService;
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
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateDirectorDto createDirector(CreateDirectorDto createDirectorDto) {
        Director director = modelMapper.map(createDirectorDto, Director.class);
        boolean existDirector = directorRepository.existsByName(director.getName());

        if(existDirector)
            throw new FilmupiaApiException("Director name " + director.getName() + " already exists");

        directorRepository.save(director);
        return modelMapper.map(director, CreateDirectorDto.class);
    }

    @Override
    public void updateDirector(Long directorId, UpdateDirectorDto updateDirectorDto) {
        Director director = directorRepository.findById(directorId).orElseThrow(
                () -> new ResourceNotFoundException("Director", "id", directorId));

        modelMapper.map(updateDirectorDto, director);
        directorRepository.save(director);
    }

    @Override
    public void deleteDirector(Long directorId) {
        Director director = directorRepository.findById(directorId).orElseThrow(
                () -> new ResourceNotFoundException("Director", "id", directorId));
        directorRepository.delete(director);
    }

    @Override
    public DirectorDto getDirector(Long directorId) {
        Director director = directorRepository.findById(directorId).orElseThrow(
                () -> new ResourceNotFoundException("Director", "id", directorId));

        return modelMapper.map(director, DirectorDto.class);
    }

    @Override
    public List<DirectorDto> getDirectors() {
        List<Director> directors = directorRepository.findAll();
        return directors.stream()
                .map(director -> modelMapper.map(director, DirectorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorDto> getDirectorsWithPagination(int pageNumber, int pageSize) {
        if (pageNumber < 1)
            throw new FilmupiaApiException("Page number should be greater than 0");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Director> directors = directorRepository.findAll(pageable).getContent();
        return directors.stream()
                .map(director -> modelMapper.map(director, DirectorDto.class))
                .collect(Collectors.toList());
    }
}
