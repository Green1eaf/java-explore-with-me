package ru.practicum.ewm.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.service.dto.CategoryDto;
import ru.practicum.ewm.service.model.Category;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toDto(Category entity);

    Category fromDto(CategoryDto dto);
}
