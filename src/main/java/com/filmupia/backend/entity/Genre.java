package com.filmupia.backend.entity;

import com.filmupia.backend.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genres")
public class Genre extends BaseEntity {

    @NotBlank(message = "Genre name cannot be blank")
    private String name;
}
