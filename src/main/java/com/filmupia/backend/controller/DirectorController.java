package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.actor.ActorDto;
import com.filmupia.backend.model.director.CreateDirectorDto;
import com.filmupia.backend.model.director.DirectorDto;
import com.filmupia.backend.model.director.UpdateDirectorDto;
import com.filmupia.backend.service.DirectorService;
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
@RequestMapping("/api/v1/directors")
@RequiredArgsConstructor
@Tag(name = "Directors Management", description = "Manage directors in the system")
public class DirectorController {
    private final DirectorService directorService;

    @Operation(summary = "Get all directors",
            description = "Retrieve a list of all directors in the system")
    @GetMapping
    public ResponseEntity<List<DirectorDto>> getAll() {
        return ResponseEntity.ok()
                .body(directorService.getDirectors());
    }

    @Operation(
            summary = "Get director by ID",
            description = "Retrieve the director details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Director found",
                            content = @Content(schema = @Schema(implementation = ActorDto.class))),
                    @ApiResponse(responseCode = "404", description = "Director not found")
            }
    )
    @GetMapping("/{directorId}")
    public ResponseEntity<DirectorDto> getById(
            @Parameter(description = "ID of the director to be retrieved")
            @PathVariable("directorId") Long directorId
    ) {
        return ResponseEntity.ok()
                .body(directorService.getDirector(directorId));
    }

    @Operation(
            summary = "Get directors with pagination",
            description = "Retrieve directors with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of directors",
                            content = @Content(schema = @Schema(implementation = ActorDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<DirectorDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of actors per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(directorService.getDirectorsWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new director",
            description = "Add a new director to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Director created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Valid @RequestBody CreateDirectorDto createDirectorDto
    ) {
        directorService.createDirector(createDirectorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Update an existing director",
            description = "Update the details of an existing director by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Director updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Director not found")
            }
    )
    @PutMapping("/{directorId}")
    public ResponseEntity<ResponseModel> update(
            @Parameter(description = "ID of the director to be updated")
            @PathVariable("directorId") Long directorId,
            @Valid @RequestBody UpdateDirectorDto updateDirectorDto
    ) {
        directorService.updateDirector(directorId, updateDirectorDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @Operation(
            summary = "Delete an actor by ID",
            description = "Delete an director from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Director deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Director not found")
            }
    )
    @DeleteMapping("/{directorId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "ID of the director to be deleted")
            @PathVariable("directorId") Long directorId
    ) {
        directorService.deleteDirector(directorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
