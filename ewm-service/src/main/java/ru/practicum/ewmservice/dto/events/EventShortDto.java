package ru.practicum.ewmservice.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.ewmservice.dto.categories.CategoryDto;
import ru.practicum.ewmservice.dto.users.UsersShortDto;

import java.time.LocalDateTime;

public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Long id;
    private UsersShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
