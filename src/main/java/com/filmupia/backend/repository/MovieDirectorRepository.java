package com.filmupia.backend.repository;

import com.filmupia.backend.entity.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDirectorRepository extends JpaRepository<MovieDirector, Long> {
}
