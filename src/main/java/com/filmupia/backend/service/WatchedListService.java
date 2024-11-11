package com.filmupia.backend.service;

import com.filmupia.backend.model.watchedList.CreateWatchedListDto;
import com.filmupia.backend.model.watchedList.WatchedListDto;

import java.util.List;

public interface WatchedListService {
    CreateWatchedListDto createWatchedList(String token, CreateWatchedListDto createWatchedListDto);
    void deleteWatchedList(String token, Long watchedListId);
    WatchedListDto getWatchedList(Long watchedListId);
    List<WatchedListDto> getWatchedLists();
    List<WatchedListDto> getWatchedListsWithPagination(int pageNumber, int pageSize);
}
