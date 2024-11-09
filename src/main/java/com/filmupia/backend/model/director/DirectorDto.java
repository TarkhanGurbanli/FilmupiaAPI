package com.filmupia.backend.model.director;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {
    private Long id;
    private String name;
    private String imageUrl;
}
