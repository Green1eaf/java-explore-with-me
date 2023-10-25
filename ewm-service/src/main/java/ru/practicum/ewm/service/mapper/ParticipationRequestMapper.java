package ru.practicum.ewm.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.service.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.model.ParticipationRequest;

@Mapper
public interface ParticipationRequestMapper {
    ParticipationRequestMapper INSTANCE = Mappers.getMapper(ParticipationRequestMapper.class);

    @Mapping(target = "requester", source = "entity.requester.id")
    @Mapping(target = "event", source = "entity.event.id")
    ParticipationRequestDto toDto(ParticipationRequest entity);
}
