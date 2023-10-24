package ru.practicum.ewm.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.CategoryDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryControllerPublic {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll(@Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                     @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return categoryService.findAll(from, size);
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable long id) {
        return categoryService.getById(id);
    }
}
