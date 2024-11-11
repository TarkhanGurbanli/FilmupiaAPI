package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.movie.CreateMovieDto;
import com.filmupia.backend.model.movie.MovieDto;
import com.filmupia.backend.model.movie.UpdateMovieDto;
import com.filmupia.backend.service.MovieService;
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
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Movies Management", description = "Manage movies in the system")
public class MovieController {
    private final MovieService movieService;

    @Operation(summary = "Get all movies",
            description = "Retrieve a list of all movie in the system")
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        return ResponseEntity.ok()
                .body(movieService.getMovies());
    }

    @Operation(
            summary = "Get movie by ID",
            description = "Retrieve the movie details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie found",
                            content = @Content(schema = @Schema(implementation = MovieDto.class))),
                    @ApiResponse(responseCode = "404", description = "Movie not found")
            }
    )
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getById(
            @Parameter(description = "ID of the movie to be retrieved")
            @PathVariable("movieId") Long movieId
    ) {
        return ResponseEntity.ok()
                .body(movieService.getMovie(movieId));
    }

    @Operation(
            summary = "Get movies with pagination",
            description = "Retrieve movies with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of movies",
                            content = @Content(schema = @Schema(implementation = MovieDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<MovieDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of actors per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(movieService.getMoviesWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new movie",
            description = "Add a new movie to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Movie created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateMovieDto createMovieDto
    ) {
        movieService.createMovie(createMovieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Update an existing movie",
            description = "Update the details of an existing movie by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie not found")
            }
    )
    @PutMapping("/{movieId}")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "ID of the movie to be updated")
            @PathVariable("movieId") Long movieId,
            @Valid @RequestBody UpdateMovieDto updateMovieDto
    ) {
        movieService.updateMovie(movieId, updateMovieDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @Operation(
            summary = "Delete an movie by ID",
            description = "Delete an movie from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie not found")
            }
    )
    @DeleteMapping("/{movieId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "ID of the movie to be deleted")
            @PathVariable("movieId") Long movieId
    ) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}