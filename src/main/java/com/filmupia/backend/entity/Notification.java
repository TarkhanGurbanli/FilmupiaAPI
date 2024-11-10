package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Column(nullable = false)
    @NotNull(message = "Message cannot be null")
    private String message;

    @Column(nullable = false)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;
}
