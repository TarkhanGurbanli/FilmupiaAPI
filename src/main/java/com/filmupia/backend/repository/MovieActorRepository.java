package com.filmupia.backend.repository;

import com.filmupia.backend.entity.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieActorRepository extends JpaRepository<MovieActor, Long> {
}
