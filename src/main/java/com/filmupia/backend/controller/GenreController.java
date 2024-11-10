package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.genre.CreateGenreDto;
import com.filmupia.backend.model.genre.GenreDto;
import com.filmupia.backend.model.genre.UpdateGenreDto;
import com.filmupia.backend.service.GenreService;
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
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Genres Management", description = "Manage genres in the system")
public class GenreController {
    private final GenreService genreService;

    @Operation(summary = "Get all genres",
            description = "Retrieve a list of all genres in the system")
    @GetMapping
    public ResponseEntity<List<GenreDto>> getAll() {
        return ResponseEntity.ok()
                .body(genreService.getGenres());
    }

    @Operation(
            summary = "Get genre by ID",
            description = "Retrieve the genre details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Genre found",
                            content = @Content(schema = @Schema(implementation = GenreDto.class))),
                    @ApiResponse(responseCode = "404", description = "Genre not found")
            }
    )
    @GetMapping("/{genreId}")
    public ResponseEntity<GenreDto> getById(
            @Parameter(description = "ID of the genre to be retrieved")
            @PathVariable("genreId") Long genreId
    ) {
        return ResponseEntity.ok()
                .body(genreService.getGenre(genreId));
    }

    @Operation(
            summary = "Get genres with pagination",
            description = "Retrieve genres with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of genres",
                            content = @Content(schema = @Schema(implementation = GenreDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<GenreDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of actors per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(genreService.getGenresWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new genre",
            description = "Add a new genre to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Genre created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateGenreDto createGenreDto
    ) {
        genreService.createGenre(createGenreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Update an existing genre",
            description = "Update the details of an existing genre by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Genre updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Genre not found")
            }
    )
    @PutMapping("/{genreId}")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "ID of the genre to be updated")
            @PathVariable("genreId") Long genreId,
            @Valid @RequestBody UpdateGenreDto updateGenreDto
    ) {
        genreService.updateGenre(genreId, updateGenreDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @Operation(
            summary = "Delete an genre by ID",
            description = "Delete an genre from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Genre not found")
            }
    )
    @DeleteMapping("/{genreId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "ID of the genre to be deleted")
            @PathVariable("genreId") Long genreId
    ) {
        genreService.deleteGenre(genreId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
