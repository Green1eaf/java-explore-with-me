package ru.practicum.ewm.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.service.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByLatAndLon(float lat, float lon);
}