--DROP TABLE city

CREATE TABLE city
(
    id numeric(10,0) NOT NULL DEFAULT nextval('city_id_seq'),
    name character varying(50) NOT NULL,
    CONSTRAINT city_pkey PRIMARY KEY (id)
)