package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.EventFullDto;
import ru.practicum.ewm.service.dto.EventUpdateAdminRequest;
import ru.practicum.ewm.service.model.EventState;
import ru.practicum.ewm.service.util.Constants;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventControllerAdmin {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> get(@RequestParam(required = false) List<Long> users,
                                  @RequestParam(required = false) List<EventState> states,
                                  @RequestParam(required = false) List<Long> categories,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                  @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                  @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.findAllByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{id}")
    public EventFullDto update(@PathVariable long id,
                               @Valid @RequestBody EventUpdateAdminRequest updateEventAdminRequest) {
        return eventService.updateByAdmin(id, updateEventAdminRequest);
    }
}