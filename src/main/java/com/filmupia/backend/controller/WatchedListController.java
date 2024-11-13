package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.watchedList.CreateWatchedListDto;
import com.filmupia.backend.model.watchedList.WatchedListDto;
import com.filmupia.backend.service.WatchedListService;
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
@RequestMapping("/api/v1/watched_list")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "WatchedList Management", description = "Manage watched_list in the system")
public class WatchedListController {
    private final WatchedListService watchedListService;

    @Operation(summary = "Get all watched_lists",
            description = "Retrieve a list of all watched_lists in the system")
    @GetMapping
    public ResponseEntity<List<WatchedListDto>> getAll() {
        return ResponseEntity.ok()
                .body(watchedListService.getWatchedLists());
    }

    @Operation(
            summary = "Get watched_list by ID",
            description = "Retrieve the watched_list details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Watched List found",
                            content = @Content(schema = @Schema(implementation = WatchedListDto.class))),
                    @ApiResponse(responseCode = "404", description = "Watched list not found")
            }
    )
    @GetMapping("/{watchedListId}")
    public ResponseEntity<WatchedListDto> getById(
            @Parameter(description = "ID of the watched_list to be retrieved")
            @PathVariable("watchedListId") Long watchedListId
    ) {
        return ResponseEntity.ok()
                .body(watchedListService.getWatchedList(watchedListId));
    }

    @Operation(
            summary = "Get watched_lists with pagination",
            description = "Retrieve watched_lists with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of watched_lists",
                            content = @Content(schema = @Schema(implementation = WatchedListDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<WatchedListDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of watched_lists per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(watchedListService.getWatchedListsWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new watched_list",
            description = "Add a new watched_list to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Watched list created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateWatchedListDto createWatchedListDto
    ) throws Exception {
        watchedListService.createWatchedList(token, createWatchedListDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Delete an watched_list by ID",
            description = "Delete an watched_list from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Watched list deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Watched list not found")
            }
    )
    @DeleteMapping("/{watchedListId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the watched_list to be deleted")
            @PathVariable("watchedListId") Long watchedListId
    ) throws Exception {
        watchedListService.deleteWatchedList(token, watchedListId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}