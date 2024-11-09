package com.filmupia.backend.repository;

import com.filmupia.backend.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    boolean existsByFullName(String fullName);
    Page<Actor> findByFullNameIgnoreCaseStartingWith(String fullName, Pageable pageable);
}
