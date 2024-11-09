package com.filmupia.backend.repository;

import com.filmupia.backend.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    boolean existsByName(String name);
}
