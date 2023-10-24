package ru.practicum.ewm.service.participationRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.exception.ConflictException;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.EventState;
import ru.practicum.ewm.service.model.ParticipationRequest;
import ru.practicum.ewm.service.model.User;
import ru.practicum.ewm.service.repository.EventRepository;
import ru.practicum.ewm.service.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.service.model.ParticipationRequestState.*;

@Service
@RequiredArgsConstructor
public class ParticipationRequestService {
    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public List<ParticipationRequestDto> findAll(long userId) {
        findUserByIdIfExists(userId);

        return participationRequestRepository.findAllByRequesterId(userId).stream()
                .map(ParticipationRequestMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public ParticipationRequestDto create(long userId, long eventId) {
        User requester = findUserByIdIfExists(userId);
        Event event = findEventByIdIfExists(eventId);

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Event initiator cannot submit a participation request for own event");
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Cannot participate in an unpublished event");
        }

        if (event.getParticipantLimit() > 0) {
            if (event.getParticipantLimit() <= participationRequestRepository.countByEventIdAndStatus(eventId, CONFIRMED)) {
                throw new ConflictException("The number of participation requests has exceeded the limit for the event");
            }
        }

        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .requester(requester)
                .event(event)
                .created(LocalDateTime.now())
                .status(event.getRequestModeration() && !event.getParticipantLimit().equals(0) ? PENDING : CONFIRMED)
                .build();

        return ParticipationRequestMapper.INSTANCE.toDto(participationRequestRepository.save(participationRequest));
    }

    public ParticipationRequestDto update(long userId, long requestId) {
        findUserByIdIfExists(userId);
        ParticipationRequest participationRequest = findParticipationRequestByIdIfExists(requestId);

        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new NotFoundException("No events available for editing were found");
        }

        participationRequest.setStatus(CANCELED);

        return ParticipationRequestMapper.INSTANCE.toDto(participationRequestRepository.save(participationRequest));
    }

    private ParticipationRequest findParticipationRequestByIdIfExists(long id) {
        return participationRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participation request with id=" + id + " not found"));
    }

    private User findUserByIdIfExists(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found"));
    }

    private Event findEventByIdIfExists(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " not found"));
    }
}
