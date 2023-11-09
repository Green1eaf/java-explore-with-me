package ru.practicum.ewm.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.service.dto.CommentDto;
import ru.practicum.ewm.service.model.Comment;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "userName", source = "entity.user.name")
    CommentDto toDto(Comment entity);
}
