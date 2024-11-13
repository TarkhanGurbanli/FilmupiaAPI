package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Movie;
import com.filmupia.backend.entity.User;
import com.filmupia.backend.entity.WatchedList;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.auth.JwtModel;
import com.filmupia.backend.model.watchedList.CreateWatchedListDto;
import com.filmupia.backend.model.watchedList.WatchedListDto;
import com.filmupia.backend.repository.MovieRepository;
import com.filmupia.backend.repository.UserRepository;
import com.filmupia.backend.repository.WatchedListRepository;
import com.filmupia.backend.service.WatchedListService;
import com.filmupia.backend.service.auth.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WatchedListServiceImpl implements WatchedListService {

    private final WatchedListRepository watchedListRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public CreateWatchedListDto createWatchedList(String token, CreateWatchedListDto createWatchedListDto)
            throws Exception {
        JwtModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Movie movie = movieRepository.findById(createWatchedListDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", createWatchedListDto.getMovieId()));

        WatchedList watchedList = new WatchedList();
        watchedList.setUser(user);
        watchedList.setMovie(movie);

        WatchedList savedWatchedList = watchedListRepository.save(watchedList);

        CreateWatchedListDto dto = new CreateWatchedListDto();
        dto.setMovieId(savedWatchedList.getMovie().getId());
        return dto;
    }

    @Override
    public void deleteWatchedList(String token, Long watchedListId) throws Exception {
        JwtModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        WatchedList watchedList = watchedListRepository.findById(watchedListId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchedList", "id", watchedListId));

        watchedListRepository.delete(watchedList);
    }

    @Override
    public WatchedListDto getWatchedList(Long watchedListId) {
        WatchedList watchedList = watchedListRepository.findById(watchedListId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchedList", "id", watchedListId));

        WatchedListDto dto = new WatchedListDto();
        dto.setId(watchedList.getId());
        dto.setMovieId(watchedList.getMovie().getId());
        dto.setMovieName(watchedList.getMovie().getName());
        dto.setImageUrl(watchedList.getMovie().getImageUrl());
        dto.setRating(watchedList.getMovie().getRating());
        dto.setPublicationYear(watchedList.getMovie().getPublicationYear());

        return dto;
    }

    @Override
    public List<WatchedListDto> getWatchedLists() {
        return watchedListRepository.findAll().stream()
                .map(watchedList -> {
                    WatchedListDto dto = new WatchedListDto();
                    dto.setId(watchedList.getId());
                    dto.setMovieId(watchedList.getMovie().getId());
                    dto.setMovieName(watchedList.getMovie().getName());
                    dto.setImageUrl(watchedList.getMovie().getImageUrl());
                    dto.setRating(watchedList.getMovie().getRating());
                    dto.setPublicationYear(watchedList.getMovie().getPublicationYear());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<WatchedListDto> getWatchedListsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return watchedListRepository.findAll(pageable).stream()
                .map(watchedList -> {
                    WatchedListDto dto = new WatchedListDto();
                    dto.setId(watchedList.getId());
                    dto.setMovieId(watchedList.getMovie().getId());
                    dto.setMovieName(watchedList.getMovie().getName());
                    dto.setImageUrl(watchedList.getMovie().getImageUrl());
                    dto.setRating(watchedList.getMovie().getRating());
                    dto.setPublicationYear(watchedList.getMovie().getPublicationYear());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
