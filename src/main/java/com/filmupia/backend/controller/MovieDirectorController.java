package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.movieDirector.CreateMovieDirectorDto;
import com.filmupia.backend.model.movieDirector.MovieDirectorDto;
import com.filmupia.backend.model.movieDirector.UpdateMovieDirectorDto;
import com.filmupia.backend.service.MovieDirectorService;
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
@RequestMapping("/api/v1/movie_directors")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "MovieDirectors Management", description = "Manage movie_directors in the system")
public class MovieDirectorController {
    private final MovieDirectorService movieDirectorService;

    @Operation(summary = "Get all movie_directors",
            description = "Retrieve a list of all movie_directors in the system")
    @GetMapping
    public ResponseEntity<List<MovieDirectorDto>> getAll() {
        return ResponseEntity.ok()
                .body(movieDirectorService.getMovieDirectors());
    }

    @Operation(
            summary = "Get movie_director by ID",
            description = "Retrieve the movie_director details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie Director found",
                            content = @Content(schema = @Schema(implementation = MovieDirectorDto.class))),
                    @ApiResponse(responseCode = "404", description = "Movie Director not found")
            }
    )
    @GetMapping("/{movieDirectorId}")
    public ResponseEntity<MovieDirectorDto> getById(
            @Parameter(description = "ID of the movie_directors to be retrieved")
            @PathVariable("movieDirectorId") Long movieDirectorId
    ) {
        return ResponseEntity.ok()
                .body(movieDirectorService.getMovieDirector(movieDirectorId));
    }

    @Operation(
            summary = "Get movie_directors with pagination",
            description = "Retrieve movie_directors with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of movie_directors",
                            content = @Content(schema = @Schema(implementation = MovieDirectorDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<MovieDirectorDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of movie_directors per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(movieDirectorService.getMovieDirectorsWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new movie_director",
            description = "Add a new movie_director to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Movie Director created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateMovieDirectorDto createMovieDirectorDto
    ) {
        movieDirectorService.createMovieDirector(createMovieDirectorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Update an existing movie_director",
            description = "Update the details of an existing movie_director by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie Director updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie Director not found")
            }
    )
    @PutMapping("/{movieDirectorId}")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "ID of the movie_director to be updated")
            @PathVariable("movieDirectorId") Long movieDirectorId,
            @Valid @RequestBody UpdateMovieDirectorDto updateMovieDirectorDto
    ) {
        movieDirectorService.updateMovieDirector(movieDirectorId, updateMovieDirectorDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @Operation(
            summary = "Delete an movie_director by ID",
            description = "Delete an movie_director from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Movie Director deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie Director not found")
            }
    )
    @DeleteMapping("/{movieDirectorId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "ID of the movie_actors to be deleted")
            @PathVariable("movieDirectorId") Long movieDirectorId
    ) {
        movieDirectorService.deleteMovieDirector(movieDirectorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
