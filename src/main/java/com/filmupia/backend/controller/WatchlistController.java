package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.watchlist.CreateWatchlistDto;
import com.filmupia.backend.model.watchlist.WatchlistDto;
import com.filmupia.backend.service.WatchlistService;
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
@RequestMapping("/api/v1/watchlist")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "WatchList Management", description = "Manage watchlist in the system")
public class WatchlistController {
    private final WatchlistService watchlistService;

    @Operation(summary = "Get all watchlists",
            description = "Retrieve a list of all watchlists in the system")
    @GetMapping
    public ResponseEntity<List<WatchlistDto>> getAll() {
        return ResponseEntity.ok()
                .body(watchlistService.getWatchlists());
    }

    @Operation(
            summary = "Get watched_list by ID",
            description = "Retrieve the watchlist details by their unique ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Watch List found",
                            content = @Content(schema = @Schema(implementation = WatchlistDto.class))),
                    @ApiResponse(responseCode = "404", description = "Watch list not found")
            }
    )
    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchlistDto> getById(
            @Parameter(description = "ID of the watchlist to be retrieved")
            @PathVariable("watchListId") Long watchListId
    ) {
        return ResponseEntity.ok()
                .body(watchlistService.getWatchlist(watchListId));
    }

    @Operation(
            summary = "Get watchlists with pagination",
            description = "Retrieve watchlists with pagination support",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of watchlists",
                            content = @Content(schema = @Schema(implementation = WatchlistDto.class)))
            }
    )
    @GetMapping("/page")
    public ResponseEntity<List<WatchlistDto>> getPageNumber(
            @Parameter(description = "Page number to fetch", example = "1")
            @RequestParam("pageNumber") Optional<Integer> pageNumber,

            @Parameter(description = "Number of watchlists per page", example = "10")
            @RequestParam("pageSize") Optional<Integer> pageSize
    ) {
        int page = pageNumber.orElse(1);
        int size = pageSize.orElse(10);
        return ResponseEntity.ok()
                .body(watchlistService.getWatchlistsWithPagination(page, size));
    }

    @Operation(
            summary = "Create a new watchlist",
            description = "Add a new watchlist to the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Watch list created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseModel> create(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateWatchlistDto createWatchlistDto
    ) throws Exception {
        watchlistService.createWatchlist(token, createWatchlistDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @Operation(
            summary = "Delete an watchlist by ID",
            description = "Delete an watchlist from the system by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Watch list deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Watch list not found")
            }
    )
    @DeleteMapping("/{watchListId}")
    public ResponseEntity<ResponseModel> delete(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @Parameter(description = "ID of the watchlist to be deleted")
            @PathVariable("watchListId") Long watchListId
    ) throws Exception {
        watchlistService.deleteWatchlist(token, watchListId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}