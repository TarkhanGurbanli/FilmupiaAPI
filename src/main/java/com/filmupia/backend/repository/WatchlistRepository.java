package com.filmupia.backend.repository;

import com.filmupia.backend.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchList, Long> {
}
