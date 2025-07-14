CREATE TABLE public.categories
(
    id smallserial NOT NULL,
    name character varying(255)[] NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.categories
    OWNER to postgres;

ALTER TABLE IF EXISTS public.profiles DROP COLUMN IF EXISTS bio;

ALTER TABLE IF EXISTS public.profiles DROP COLUMN IF EXISTS phone_number;

ALTER TABLE IF EXISTS public.profiles
    ADD COLUMN bio character varying(255);

ALTER TABLE IF EXISTS public.profiles
    ADD COLUMN phone_number character varying(255);

ALTER TABLE IF EXISTS public.addresses DROP COLUMN IF EXISTS street;

ALTER TABLE IF EXISTS public.addresses DROP COLUMN IF EXISTS city;

ALTER TABLE IF EXISTS public.addresses DROP COLUMN IF EXISTS zip;

ALTER TABLE IF EXISTS public.addresses DROP COLUMN IF EXISTS state;

ALTER TABLE IF EXISTS public.addresses
    ADD COLUMN street character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.addresses
    ADD COLUMN city character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.addresses
    ADD COLUMN zip character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.addresses
    ADD COLUMN state character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.categories DROP COLUMN IF EXISTS name;

ALTER TABLE IF EXISTS public.categories
    ADD COLUMN name character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.tags DROP COLUMN IF EXISTS name;

ALTER TABLE IF EXISTS public.tags
    ADD COLUMN name character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.users DROP COLUMN IF EXISTS name;

ALTER TABLE IF EXISTS public.users DROP COLUMN IF EXISTS email;

ALTER TABLE IF EXISTS public.users DROP COLUMN IF EXISTS password;

ALTER TABLE IF EXISTS public.users
    ADD COLUMN name character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.users
    ADD COLUMN email character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.users
    ADD COLUMN password character varying(255) NOT NULL;

ALTER TABLE IF EXISTS public.users
    ADD CONSTRAINT users_email_unique UNIQUE (email);


CREATE TABLE public.products
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    price numeric(10, 2) NOT NULL,
    category_id smallserial,
    PRIMARY KEY (id),
    CONSTRAINT products_category_fk FOREIGN KEY (category_id)
        REFERENCES public.categories (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
        NOT VALID
);

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;