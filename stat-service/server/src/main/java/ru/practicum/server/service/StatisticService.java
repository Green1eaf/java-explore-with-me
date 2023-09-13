package ru.practicum.server.service;

import ru.pracitcum.dto.EndpointHitDto;
import ru.pracitcum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    EndpointHitDto create(EndpointHitDto dto);
}
