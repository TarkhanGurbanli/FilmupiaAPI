package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.WatchedList;
import com.filmupia.backend.model.watchedList.CreateWatchedListDto;
import com.filmupia.backend.model.watchedList.WatchedListDto;
import com.filmupia.backend.repository.MovieRepository;
import com.filmupia.backend.repository.WatchedListRepository;
import com.filmupia.backend.service.WatchedListService;
import com.filmupia.backend.service.auth.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WatchedListServiceImpl implements WatchedListService {

    private final WatchedListRepository watchedListRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    public CreateWatchedListDto createWatchedList(String token, CreateWatchedListDto createWatchedListDto) {
        return null;
    }

    @Override
    public void deleteWatchedList(String token, Long watchedListId) {

    }

    @Override
    public WatchedListDto getWatchedList(Long watchedListId) {
        return null;
    }

    @Override
    public List<WatchedListDto> getWatchedLists() {
        return List.of();
    }

    @Override
    public List<WatchedListDto> getWatchedListsWithPagination(int pageNumber, int pageSize) {
        return List.of();
    }
}
