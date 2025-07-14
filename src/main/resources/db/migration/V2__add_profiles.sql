CREATE TABLE public.profiles
(
    id bigint NOT NULL,
    bio character varying(255)[],
    phone_number character varying(255)[],
    date_of_birth timestamp with time zone,
    loyalty_points int DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT profile_id_user_id_fk FOREIGN KEY (id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.profiles
    OWNER to postgres;
