package ru.practicum.ewm.stats.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "endpoints_hits")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hit_id")
    private Long id;

    @Column(name = "hit_app")
    private String app;

    @Column(name = "hit_uri")
    private String uri;

    @Column(name = "hit_ip")
    private String ip;

    @Column(name = "hit_timestamp")
    private LocalDateTime hitTimestamp;
}