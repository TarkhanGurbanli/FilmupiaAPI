package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Actor;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.actor.ActorDto;
import com.filmupia.backend.model.actor.CreateActorDto;
import com.filmupia.backend.model.actor.UpdateActorDto;
import com.filmupia.backend.repository.ActorRepository;
import com.filmupia.backend.service.ActorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateActorDto createActor(CreateActorDto createActorDto) {
        Actor actor = modelMapper.map(createActorDto, Actor.class);
        boolean existName = actorRepository.existsByFullName(actor.getFullName());

        if (existName)
            throw new FilmupiaApiException("Actor name '" + actor.getFullName() + "' already exists");

        actorRepository.save(actor);
        return modelMapper.map(actor, CreateActorDto.class);
    }

    @Override
    public void updateActor(Long actorId, UpdateActorDto updateActorDto) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new ResourceNotFoundException("Actor", "id", actorId));

        modelMapper.map(updateActorDto, actor);
        actorRepository.save(actor);
    }

    @Override
    public void deleteActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new ResourceNotFoundException("Actor", "id", actorId));
        actorRepository.delete(actor);
    }

    @Override
    public ActorDto getActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new ResourceNotFoundException("Actor", "id", actorId));
        return modelMapper.map(actor, ActorDto.class);
    }

    @Override
    public List<ActorDto> getActors() {
        List<Actor> actors = actorRepository.findAll();
        return actors.stream()
                .map(actor -> modelMapper
                        .map(actor, ActorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActorDto> getActorsWithPagination(int pageNumber, int pageSize) {

        if (pageNumber < 1)
            throw new FilmupiaApiException("Page number should be greater than 0");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Actor> actors = actorRepository.findAll(pageable).getContent();

        return actors.stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActorDto> searchActors(String searchTerm, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Actor> actorPage = actorRepository.findByFullNameIgnoreCaseStartingWith(searchTerm, pageable);

        return actorPage.stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(Collectors.toList());
    }
}
