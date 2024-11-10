package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.movieActor.CreateMovieActorDto;
import com.filmupia.backend.model.movieActor.MovieActorDto;
import com.filmupia.backend.model.movieActor.UpdateMovieActorDto;
import com.filmupia.backend.service.MovieActorService;
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
@RequestMapping("/api/v1/movie_actors")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "MovieActors Management", description = "Manage movie_actors in the system")
public class MovieActorController {
    private final MovieActorService movieActorService;

    @Operation(summary = "Get all movie_actors",
            description = "Retrieve a list of all movie_actor in the system")
    @GetMapping
    public ResponseEntity<List<MovieActorDto>> getAll() {
        return ResponseEntity.ok()
                .body(movieActorService.getMovieActors());
    }

    @Operation(
            summary = "Get movie_actors by ID",
            description = "Retrieve the movie_actors details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie Actor found",
                            content = @Content(schema = @Schema(implementation = MovieActorDto.class))),
                    @ApiResponse(responseCode = "404", description = "Movie Actor not found")
            }
    )
    @GetMapping("/{movieActorId}")
    public ResponseEntity<MovieActorDto> getById(
            @Parameter(description = "ID of the movie_actors to be retrieved")
            @PathVariable("movieActorId") Long movieActorId
    ) {
        return ResponseEntity.ok()
                .body(movieActorService.getMovieActor(movieActorId));
    }

    @Operation(
            summary = "Get movie_actors with pagination",
            description = "Retrieve movie_actors with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of movie_actors",
                            content = @Content(schema = @Schema(implementation = MovieActorDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<MovieActorDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of movie_actors per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(movieActorService.getMovieActorsWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new movie_actor",
            description = "Add a new movie_actor to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Movie Actor created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateMovieActorDto createMovieActorDto
    ) {
        movieActorService.createMovieActor(createMovieActorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Update an existing movie_actor",
            description = "Update the details of an existing movie_actor by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie Actor updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie Actor not found")
            }
    )
    @PutMapping("/{movieActorId}")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "ID of the movie_actor to be updated")
            @PathVariable("movieActorId") Long movieActorId,
            @Valid @RequestBody UpdateMovieActorDto updateMovieActorDto
    ) {
        movieActorService.updateMovieActor(movieActorId, updateMovieActorDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @Operation(
            summary = "Delete an movie_actor by ID",
            description = "Delete an movie_actor from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Movie Actor deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie Actor not found")
            }
    )
    @DeleteMapping("/{movieActorId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "ID of the movie_actor to be deleted")
            @PathVariable("movieActorId") Long movieActorId
    ) {
        movieActorService.deleteMovieActor(movieActorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}

