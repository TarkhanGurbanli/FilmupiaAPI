package com.filmupia.backend.service;

import com.filmupia.backend.model.director.CreateDirectorDto;
import com.filmupia.backend.model.director.DirectorDto;
import com.filmupia.backend.model.director.UpdateDirectorDto;

import java.util.List;

public interface DirectorService {
    CreateDirectorDto createDirector(CreateDirectorDto createDirectorDto);
    void updateDirector(Long directorId, UpdateDirectorDto updateDirectorDto);
    void deleteDirector(Long directorId);
    DirectorDto getDirector(Long directorId);
    List<DirectorDto> getDirectors();
    List<DirectorDto> getDirectorsWithPagination(int pageNumber, int pageSize);
}
