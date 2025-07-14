CREATE TABLE public.tags
(
    id bigserial NOT NULL,
    name character varying(255)[] NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.tags
    OWNER to postgres;


CREATE TABLE public.user_tags
(
    user_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    PRIMARY KEY (user_id, tag_id),
    CONSTRAINT user_tags_user_id_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT user_tags_tag_id_fk FOREIGN KEY (tag_id)
        REFERENCES public.tags (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE IF EXISTS public.user_tags
    OWNER to postgres;