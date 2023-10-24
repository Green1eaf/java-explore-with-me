package ru.practicum.ewm.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.CategoryDto;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.mapper.CategoryMapper;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).getContent().stream()
                .map(CategoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getById(long id) {
        return CategoryMapper.INSTANCE.toDto(findByIdIfExists(id));
    }

    @Transactional
    public CategoryDto create(CategoryDto dto) {
        return CategoryMapper.INSTANCE.toDto(categoryRepository.save(CategoryMapper.INSTANCE.fromDto(dto)));
    }

    @Transactional
    public CategoryDto update(long id, CategoryDto dto) {
        Category stored = findByIdIfExists(id);
        Optional.ofNullable(dto.getName()).ifPresent(stored::setName);
        return CategoryMapper.INSTANCE.toDto(categoryRepository.save(stored));
    }

    @Transactional
    public void deleteById(long id) {
        findByIdIfExists(id);
        categoryRepository.deleteById(id);
    }

    private Category findByIdIfExists(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id=" + id + " not found"));
    }
}