package ru.practicum.ewm.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.dto.CompilationNewDto;
import ru.practicum.ewm.service.model.Compilation;
import ru.practicum.ewm.service.model.Event;

import java.util.List;

@Mapper(uses = EventMapper.class)
public interface CompilationMapper {
    CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    Compilation fromDto(CompilationNewDto dto, List<Event> events);

    CompilationDto toDto(Compilation entity);
}
