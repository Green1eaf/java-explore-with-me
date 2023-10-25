package ru.practicum.ewm.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.dto.CompilationNewDto;
import ru.practicum.ewm.service.dto.CompilationUpdateRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody CompilationNewDto dto) {
        return compilationService.create(dto);
    }

    @PatchMapping("/{id}")
    public CompilationDto update(@PathVariable long id,
                                 @Valid @RequestBody CompilationUpdateRequest compilationUpdateRequest) {
        return compilationService.update(id, compilationUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        compilationService.deleteById(id);
    }
}