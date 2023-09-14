CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  TEXT NOT NULL,
    email TEXT NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS users_unique_email_idx ON users (email);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS categories_unique_name_idx ON categories (name);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation         TEXT                        NOT NULL,
    category_id        BIGINT                      NOT NULL,
    confirmed_requests BIGINT,
    created_on         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description        TEXT                        NOT NULL,
    date               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id       BIGINT                      NOT NULL,
    lat                REAL                        NOT NULL,
    lon                REAL                        NOT NULL,
    paid               BOOLEAN                     NOT NULL,
    participant_limit  BIGINT,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state              TEXT                        NOT NULL,
    title              TEXT                        NOT NULL,
    views              BIGINT                      NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id     BIGINT                      NOT NULL,
    requester_id BIGINT                      NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status       TEXT                        NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_event_req UNIQUE (event_id, requester_id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title  TEXT    NOT NULL,
    pinned BOOLEAN NOT NULL,
    CONSTRAINT uq_compilations_title UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS events_compilations
(
    event_id       BIGINT REFERENCES events (id) ON DELETE CASCADE,
    compilation_id BIGINT REFERENCES compilations (id) ON DELETE CASCADE,
    CONSTRAINT pk_events_compilations PRIMARY KEY (event_id, compilation_id),
    CONSTRAINT uq_events_compilations UNIQUE (event_id, compilation_id)
)