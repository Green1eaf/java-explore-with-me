package ru.practicum.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.pracitcum.dto.EndpointHitDto;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        EndpointHitDto e = new EndpointHitDto();
        SpringApplication.run(ServiceApplication.class, args);
    }

}
