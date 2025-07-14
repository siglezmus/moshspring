CREATE TABLE public.wishlist
(
    user_id bigint NOT NULL,
    product_id bigint NOT NULL,
    PRIMARY KEY (user_id, product_id),
    CONSTRAINT wishlist_user_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT wishlist_product_fk FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE IF EXISTS public.wishlist
    OWNER to postgres;