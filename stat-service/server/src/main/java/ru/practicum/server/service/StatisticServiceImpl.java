package ru.practicum.server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pracitcum.dto.EndpointHitDto;
import ru.pracitcum.dto.ViewStatsDto;
import ru.practicum.server.exception.BadRequestException;
import ru.practicum.server.mapper.ViewStatsMapper;
import ru.practicum.server.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.server.mapper.EndpointHitMapper.toDto;
import static ru.practicum.server.mapper.EndpointHitMapper.toEntity;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new BadRequestException("Error: date of start must be before the date of end");
        }
        return (unique ? repository.findViewStatsWithUniqueIp(start, end, uris)
                : repository.findViewStats(start, end, uris)).stream()
                .map(ViewStatsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EndpointHitDto create(EndpointHitDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }
}
