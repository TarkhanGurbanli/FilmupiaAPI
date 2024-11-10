package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "watchedlists")
public class WatchedList extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @NotNull(message = "Movie cannot be null")
    private Movie movie;
}
