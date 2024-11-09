package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.actor.ActorDto;
import com.filmupia.backend.model.actor.CreateActorDto;
import com.filmupia.backend.model.actor.UpdateActorDto;
import com.filmupia.backend.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/actors")
@RequiredArgsConstructor
@Tag(name = "Actors Management", description = "Manage actors in the system")
public class ActorController {

    private final ActorService actorService;

    @Operation(summary = "Get all actors",
            description = "Retrieve a list of all actors in the system")
    @GetMapping
    public ResponseEntity<List<ActorDto>> getAll() {
        return ResponseEntity.ok()
                .body(actorService.getActors());
    }

    @Operation(
            summary = "Get actor by ID",
            description = "Retrieve the actor details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Actor found",
                            content = @Content(schema = @Schema(implementation = ActorDto.class))),
                    @ApiResponse(responseCode = "404", description = "Actor not found")
            }
    )
    @GetMapping("/{actorId}")
    public ResponseEntity<ActorDto> getById(
            @Parameter(description = "ID of the actor to be retrieved")
            @PathVariable("actorId") Long actorId
    ) {
        return ResponseEntity.ok()
                .body(actorService.getActor(actorId));
    }

    @Operation(
            summary = "Get actors with pagination",
            description = "Retrieve actors with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of actors",
                            content = @Content(schema = @Schema(implementation = ActorDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<ActorDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "0")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of actors per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(0);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(actorService.getActorsWithPagination(page, size));
    }

    @Operation(
            summary = "Search actors by name",
            description = "Search for actors whose name starts with a specific term with pagination support",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of actors matching the search term",
                            content = @Content(schema = @Schema(implementation = ActorDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "No actors found matching the search term")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<ActorDto>> search(
            @Parameter(description = "Term to search actors by (e.g. 'dway')")
            @RequestParam String searchTerm,

            @Parameter(description = "Page number to fetch", example = "0")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of actors per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(0);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(actorService.searchActors(searchTerm, page, size));
    }

    @Operation(
            summary = "Create a new actor",
            description = "Add a new actor to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Actor created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateActorDto createActorDto
    ) {
        actorService.createActor(createActorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Update an existing actor",
            description = "Update the details of an existing actor by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Actor updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Actor not found")
            }
    )
    @PutMapping("/{actorId}")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "ID of the actor to be updated")
            @PathVariable("actorId") Long actorId,
            @Valid @RequestBody UpdateActorDto updateActorDto
    ) {
        actorService.updateActor(actorId, updateActorDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @Operation(
            summary = "Delete an actor by ID",
            description = "Delete an actor from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Actor deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Actor not found")
            }
    )
    @DeleteMapping("/{actorId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "ID of the actor to be deleted")
            @PathVariable("actorId") Long actorId
    ) {
        actorService.deleteActor(actorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
