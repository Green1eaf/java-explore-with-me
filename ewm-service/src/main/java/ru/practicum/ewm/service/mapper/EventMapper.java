package ru.practicum.ewm.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.service.dto.EventFullDto;
import ru.practicum.ewm.service.dto.EventNewDto;
import ru.practicum.ewm.service.dto.EventShortDto;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.Location;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventTimestamp")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "location", source = "location")
    Event fromDto(EventNewDto dto, Category category, Location location);

    EventFullDto toFullDto(Event entity);

    EventShortDto toShortDto(Event entity);
}