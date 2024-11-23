package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Movie;
import com.filmupia.backend.entity.User;
import com.filmupia.backend.entity.WatchList;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.auth.JwtModel;
import com.filmupia.backend.model.watchlist.CreateWatchlistDto;
import com.filmupia.backend.model.watchlist.WatchlistDto;
import com.filmupia.backend.repository.MovieRepository;
import com.filmupia.backend.repository.UserRepository;
import com.filmupia.backend.repository.WatchlistRepository;
import com.filmupia.backend.service.WatchlistService;
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
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public CreateWatchlistDto createWatchlist(String token, CreateWatchlistDto createWatchlistDto)
            throws Exception {
        JwtModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Movie movie = movieRepository.findById(createWatchlistDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", createWatchlistDto.getMovieId()));

        WatchList watchList = new WatchList();
        watchList.setUser(user);
        watchList.setMovie(movie);

        WatchList savedWatchList = watchlistRepository.save(watchList);

        CreateWatchlistDto dto = new CreateWatchlistDto();
        dto.setMovieId(savedWatchList.getMovie().getId());
        return dto;    }

    @Override
    public void deleteWatchlist(String token, Long watchedListId) throws Exception {
        JwtModel jwtModel = jwtUtil.decodeToken(token);
        Long userId = jwtModel.getUserId();

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        WatchList watchList = watchlistRepository.findById(watchedListId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchedList", "id", watchedListId));

        watchlistRepository.delete(watchList);
    }

    @Override
    public WatchlistDto getWatchlist(Long watchedListId) {
        WatchList watchList = watchlistRepository.findById(watchedListId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchList", "id", watchedListId));

        WatchlistDto dto = new WatchlistDto();
        dto.setId(watchList.getId());
        dto.setMovieId(watchList.getMovie().getId());
        dto.setMovieName(watchList.getMovie().getName());
        dto.setImageUrl(watchList.getMovie().getImageUrl());
        dto.setRating(watchList.getMovie().getRating());
        dto.setPublicationYear(watchList.getMovie().getPublicationYear());
        return dto;
    }

    @Override
    public List<WatchlistDto> getWatchlists() {
        return watchlistRepository.findAll().stream()
                .map(watchedList -> {
                    WatchlistDto dto = new WatchlistDto();
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
    public List<WatchlistDto> getWatchlistsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return watchlistRepository.findAll(pageable).stream()
                .map(watchedList -> {
                    WatchlistDto dto = new WatchlistDto();
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
