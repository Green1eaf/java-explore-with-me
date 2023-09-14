package ru.practicum.ewmservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "users")
public class User extends AbstractNamedEntity {

    @Column(name = "email", nullable = false)
    private String email;
}
