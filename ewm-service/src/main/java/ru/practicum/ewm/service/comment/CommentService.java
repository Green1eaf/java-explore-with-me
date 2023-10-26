package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.CommentDto;
import ru.practicum.ewm.service.dto.CommentNewDto;
import ru.practicum.ewm.service.dto.CommentUpdateRequest;
import ru.practicum.ewm.service.exception.ForbiddenException;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.mapper.CommentMapper;
import ru.practicum.ewm.service.model.Comment;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.User;
import ru.practicum.ewm.service.repository.CommentRepository;
import ru.practicum.ewm.service.repository.EventRepository;
import ru.practicum.ewm.service.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> findAllByEventId(long eventId) {
        return commentRepository.findAllByEventId(eventId).stream()
                .map(CommentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(long userId, long eventId, CommentNewDto dto) {
        User user = findUserByIdIfExists(userId);
        Event event = findEventByIdIfExists(eventId);

        var comment = Comment.builder()
                .user(user)
                .event(event)
                .text(dto.getText())
                .createdOn(LocalDateTime.now())
                .build();

        comment = commentRepository.save(comment);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public CommentDto updateByUser(long userId, long commentId, CommentUpdateRequest updateRequest) {
        findUserByIdIfExists(userId);
        var comment = findCommentByIdIfExists(commentId);

        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User with id=" + userId + " not owner of comment with id=" + commentId);
        }

        Optional.ofNullable(updateRequest.getText()).ifPresent(comment::setText);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public CommentDto updateByAdmin(long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = findCommentByIdIfExists(commentId);

        Optional.ofNullable(updateRequest.getText()).ifPresent(comment::setText);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public void deleteByUser(long userId, long commentId) {
        findUserByIdIfExists(userId);
        Comment comment = findCommentByIdIfExists(commentId);

        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User with id=" + userId + " not owner of comment with id=" + commentId);
        }

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteByAdmin(long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Comment findCommentByIdIfExists(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + id + " not found"));
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