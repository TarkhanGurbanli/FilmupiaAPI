package com.filmupia.backend.model.actor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {
    private Long id;
    private String fullName;
    private String imageUrl;
}
