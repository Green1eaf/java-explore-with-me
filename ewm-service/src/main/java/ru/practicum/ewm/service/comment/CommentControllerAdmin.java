package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.CommentDto;
import ru.practicum.ewm.service.dto.CommentUpdateRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class CommentControllerAdmin {
    private final CommentService commentService;

    @PatchMapping("/{id}")
    public CommentDto updateByAdmin(@PathVariable long id,
                                    @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.updateByAdmin(id, commentUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAdmin(@PathVariable long id) {
        commentService.deleteByAdmin(id);
    }
}
