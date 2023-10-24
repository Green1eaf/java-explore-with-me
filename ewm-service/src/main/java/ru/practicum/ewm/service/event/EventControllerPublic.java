package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.EventFullDto;
import ru.practicum.ewm.service.dto.EventShortDto;
import ru.practicum.ewm.service.exception.BadRequestException;
import ru.practicum.ewm.service.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventControllerPublic {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> findAll(@RequestParam(defaultValue = "") String text,
                                       @RequestParam(required = false) List<Long> categories,
                                       @RequestParam(required = false) Boolean paid,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                       @RequestParam(defaultValue = "VIEWS") SortType sort,
                                       @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                       @Valid @RequestParam(defaultValue = "10") @Min(1) int size,
                                       HttpServletRequest request) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Start date must be before end date");
        }

        return eventService.findAllPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("{eventId}")
    public EventFullDto getById(@PathVariable long eventId,
                                HttpServletRequest request) {
        return eventService.getByIdPublic(eventId, request);
    }

    public enum SortType {
        EVENT_DATE,
        VIEWS
    }
}
