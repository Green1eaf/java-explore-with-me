package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.server.model.EndpointHit;
import ru.practicum.server.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<EndpointHit, Long> {

    @Query(" SELECT new ru.practicum.server.model.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.dateTime BETWEEN ?1 AND ?2 " +
            "AND (eh.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(eh.ip) DESC ")
    List<ViewStats> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(" SELECT new ru.practicum.server.model.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.dateTime BETWEEN ?1 AND ?2 " +
            "AND (eh.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(DISTINCT eh.ip) DESC ")
    List<ViewStats> findViewStatsWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
