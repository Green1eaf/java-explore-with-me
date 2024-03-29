package ru.practicum.ewm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.service.user.validation.EmailConsistency;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;

    @EmailConsistency
    @NotBlank
    @Size(min = 6, max = 254)
    private String email;
}
