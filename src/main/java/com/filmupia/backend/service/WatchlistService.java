package com.filmupia.backend.service;

import com.filmupia.backend.model.watchlist.CreateWatchlistDto;
import com.filmupia.backend.model.watchlist.WatchlistDto;

import java.util.List;

public interface WatchlistService {
    CreateWatchlistDto createWatchlist(String token, CreateWatchlistDto createWatchlistDto) throws Exception;
    void deleteWatchlist(String token, Long watchedListId) throws Exception;
    WatchlistDto getWatchlist(Long watchedListId);
    List<WatchlistDto> getWatchlists();
    List<WatchlistDto> getWatchlistsWithPagination(int pageNumber, int pageSize);
}
