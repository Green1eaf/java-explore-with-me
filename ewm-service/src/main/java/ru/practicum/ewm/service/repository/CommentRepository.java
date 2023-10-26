package ru.practicum.ewm.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.service.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(long eventId);
}