package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class EventControllerPrivate {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> findAll(@PathVariable long userId,
                                       @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                       @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.findAllByInitiator(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findById(@PathVariable long userId,
                                 @PathVariable long eventId) {
        return eventService.findByIdByInitiator(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> findParticipationRequestsByInitiator(@PathVariable long userId,
                                                                              @PathVariable long eventId) {
        return eventService.findParticipationRequestsByInitiator(userId, eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable long userId,
                               @Valid @RequestBody EventNewDto eventNewDto) {
        return eventService.create(userId, eventNewDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventInfo(@PathVariable long userId,
                                        @PathVariable long eventId,
                                        @Valid @RequestBody EventUpdateUserRequest updateEventUserRequest) {
        return eventService.updateByInitiator(userId, eventId, updateEventUserRequest);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequests(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return eventService.updateParticipationRequestsByInitiator(userId, eventId, eventRequestStatusUpdateRequest);
    }
}