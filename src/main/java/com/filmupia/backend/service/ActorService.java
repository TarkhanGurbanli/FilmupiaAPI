package com.filmupia.backend.service;

import com.filmupia.backend.model.actor.ActorDto;
import com.filmupia.backend.model.actor.CreateActorDto;
import com.filmupia.backend.model.actor.UpdateActorDto;

import java.util.List;

public interface ActorService {
    CreateActorDto createActor(CreateActorDto createActorDto);
    void updateActor(Long actorId, UpdateActorDto updateActorDto);
    void deleteActor(Long actorId);
    ActorDto getActor(Long actorId);
    List<ActorDto> getActors();
    List<ActorDto> getActorsWithPagination(int pageNumber, int pageSize);
    List<ActorDto> searchActors(String searchTerm, int pageNumber, int pageSize);
}
