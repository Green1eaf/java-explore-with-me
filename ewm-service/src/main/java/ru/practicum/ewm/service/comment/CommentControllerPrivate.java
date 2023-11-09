package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.CommentDto;
import ru.practicum.ewm.service.dto.CommentNewDto;
import ru.practicum.ewm.service.dto.CommentUpdateRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
public class CommentControllerPrivate {
    private final CommentService commentService;

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable long userId,
                             @PathVariable long eventId,
                             @Valid @RequestBody CommentNewDto dto) {
        return commentService.create(userId, eventId, dto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateByUser(@PathVariable long userId,
                                   @PathVariable long commentId,
                                   @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.updateByUser(userId, commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUser(@PathVariable long userId, @PathVariable long commentId) {
        commentService.deleteByUser(userId, commentId);
    }
}