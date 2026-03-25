CREATE TABLE episode (
                         id VARCHAR(36) PRIMARY KEY,
                         id_integration INTEGER NOT NULL UNIQUE,
                         fk_show VARCHAR(36) NOT NULL,
                         name VARCHAR(265),
                         season INTEGER,
                         number INTEGER,
                         type VARCHAR(265),
                         airdate DATE,
                         airtime TIME,
                         airstamp TIMESTAMP,
                         runtime INTEGER,
                         rating NUMERIC(5,2),
                         summary TEXT,
                         created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                         updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                         CONSTRAINT fk_episode_show
                             FOREIGN KEY (fk_show)
                                 REFERENCES show(id)
);