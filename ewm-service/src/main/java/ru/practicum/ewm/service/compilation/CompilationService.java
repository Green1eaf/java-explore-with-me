package ru.practicum.ewm.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.dto.CompilationNewDto;
import ru.practicum.ewm.service.dto.CompilationUpdateRequest;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.mapper.CompilationMapper;
import ru.practicum.ewm.service.model.Compilation;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.repository.CompilationRepository;
import ru.practicum.ewm.service.repository.EventRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<CompilationDto> findAll(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);

        return compilationRepository.findAllByPublic(pinned, pageable).stream()
                .map(CompilationMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CompilationDto getById(long id) {
        return CompilationMapper.INSTANCE.toDto(findCompilationIfExists(id));
    }

    @Transactional
    public CompilationDto create(CompilationNewDto dto) {
        List<Event> events = dto.getEvents() != null && !dto.getEvents().isEmpty() ?
                eventRepository.findAllById(dto.getEvents()) :
                Collections.emptyList();

        if (dto.getPinned() == null) {
            dto.setPinned(false);
        }
        return CompilationMapper.INSTANCE.toDto(compilationRepository.save(CompilationMapper.INSTANCE.fromDto(dto, events)));
    }

    @Transactional
    public CompilationDto update(long id, CompilationUpdateRequest compilationUpdateRequest) {
        Compilation compilation = findCompilationIfExists(id);

        if (compilationUpdateRequest.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllById(compilationUpdateRequest.getEvents()));
        }

        Optional.ofNullable(compilationUpdateRequest.getTitle()).ifPresent(compilation::setTitle);
        Optional.ofNullable(compilationUpdateRequest.getPinned()).ifPresent(compilation::setPinned);

        return CompilationMapper.INSTANCE.toDto(compilation);
    }

    @Transactional
    public void deleteById(long id) {
        findCompilationIfExists(id);
        compilationRepository.deleteById(id);
    }

    private Compilation findCompilationIfExists(long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + id + " not found"));
    }
}
