package ru.practicum.ewm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserControllerAdmin {
    private final UserService userService;

    @GetMapping()
    public List<UserDto> get(@RequestParam(required = false) List<Long> ids,
                             @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                             @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return userService.get(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId) {
        userService.delete(userId);
    }
}