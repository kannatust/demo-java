-- Table: weather

-- DROP TABLE weather;

CREATE TABLE weather
(
    id numeric(10,0) NOT NULL,
    city_id numeric(10,0),
    date_time timestamp without time zone,
    temp double precision,
    wind_speed double precision,
    humidity numeric(10,0),
    description character varying(100),
    CONSTRAINT weather_pkey PRIMARY KEY (id),
    CONSTRAINT city_id_toweather_fk FOREIGN KEY (city_id)
            REFERENCES city (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
)