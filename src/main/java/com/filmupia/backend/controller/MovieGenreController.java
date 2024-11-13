package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.movieGenre.CreateMovieGenreDto;
import com.filmupia.backend.model.movieGenre.MovieGenreDto;
import com.filmupia.backend.model.movieGenre.UpdateMovieGenreDto;
import com.filmupia.backend.service.MovieGenreService;
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
@RequestMapping("/api/v1/movie_genres")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "MovieGenres Management", description = "Manage movie_genres in the system")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    @Operation(summary = "Get all movie_genres",
            description = "Retrieve a list of all movie_genres in the system")
    @GetMapping
    public ResponseEntity<List<MovieGenreDto>> getAll() {
        return ResponseEntity.ok()
                .body(movieGenreService.getMovieGenres());
    }

    @Operation(
            summary = "Get movie_genre by ID",
            description = "Retrieve the movie_genre details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie Genre found",
                            content = @Content(schema = @Schema(implementation = MovieGenreDto.class))),
                    @ApiResponse(responseCode = "404", description = "Movie Genre not found")
            }
    )
    @GetMapping("/{movieGenreId}")
    public ResponseEntity<MovieGenreDto> getById(
            @Parameter(description = "ID of the movie_genre to be retrieved")
            @PathVariable("movieGenreId") Long movieGenreId
    ) {
        return ResponseEntity.ok()
                .body(movieGenreService.getMovieGenre(movieGenreId));
    }

    @Operation(
            summary = "Get movie_genres with pagination",
            description = "Retrieve movie_genres with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of movie_genres",
                            content = @Content(schema = @Schema(implementation = MovieGenreDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<MovieGenreDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of movie_genres per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(movieGenreService.getMovieGenresWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new movie_genre",
            description = "Add a new movie_genre to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Movie Genre created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateMovieGenreDto createMovieGenreDto
    ) {
        movieGenreService.createMovieGenre(createMovieGenreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Update an existing movie_genre",
            description = "Update the details of an existing movie_genre by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie Genre updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie Genre not found")
            }
    )
    @PutMapping("/{movieGenreId}")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "ID of the movie_genre to be updated")
            @PathVariable("movieGenreId") Long movieGenreId,
            @Valid @RequestBody UpdateMovieGenreDto updateMovieGenreDto
    ) {
        movieGenreService.updateMovieGenre(movieGenreId, updateMovieGenreDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @Operation(
            summary = "Delete an movie_genre by ID",
            description = "Delete an movie_genre from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Movie Genre deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie Genre not found")
            }
    )
    @DeleteMapping("/{movieGenreId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "ID of the movie_genre to be deleted")
            @PathVariable("movieGenreId") Long movieGenreId
    ) {
        movieGenreService.deleteMovieGenre(movieGenreId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}