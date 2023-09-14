package ru.practicum.ewmservice.dto.compilations;

import ru.practicum.ewmservice.dto.events.EventShortDto;

import java.util.List;

public class CompilationDto {
    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
