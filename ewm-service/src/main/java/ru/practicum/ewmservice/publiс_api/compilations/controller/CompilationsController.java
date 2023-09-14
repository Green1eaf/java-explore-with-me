package ru.practicum.ewmservice.publiс_api.compilations.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.compilations.CompilationDto;
import ru.practicum.ewmservice.publiс_api.compilations.service.CompilationsService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/compilations")
public class CompilationsController {
    public final CompilationsService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> findAll(@RequestParam(required = false) Boolean pinned,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        log.info("GET /compilations?pinned={}&from={}&size={}", pinned, from, size);
        return service.findAll(pinned, from, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto findById(@PathVariable Long id) {
        log.info("GET /compilations/{}", id);
        return service.findById(id);
    }
}
