package ru.practicum.ewm.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.CompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class CompilationControllerPublic {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> findAll(@RequestParam(required = false) Boolean pinned,
                                        @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                        @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return compilationService.findAll(pinned, from, size);
    }

    @GetMapping("/{id}")
    public CompilationDto getById(@PathVariable long id) {
        return compilationService.getById(id);
    }
}
