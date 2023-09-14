package ru.practicum.ewmservice.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.FIELD)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AbstractNamedEntity extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    protected String name;
}
