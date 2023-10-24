package ru.practicum.ewm.service.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.practicum.ewm.service.util.Constants;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseError {
    private HttpStatus status;
    private String message;
    private StackTraceElement[] errors;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime errorTimestamp;
}
