package com.filmupia.backend.repository;

import com.filmupia.backend.entity.WatchedList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedListRepository extends JpaRepository<WatchedList, Long> {
}
