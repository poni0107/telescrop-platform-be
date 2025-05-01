CREATE TABLE users(
    created_at timestamp without time zone,
    id SERIAL NOT NULL,
    address varchar(255),
    city varchar(255),
    email varchar(255) NOT NULL,
    full_name varchar(255),
    password_hash varchar(255) NOT NULL,
    phone_number varchar(255),
    user_type varchar(255),
    username varchar(255) NOT NULL,
    zip_code varchar(255),
    PRIMARY KEY(id),
    CONSTRAINT users_user_type_check CHECK (((user_type)::text = ANY ((ARRAY['Admin'::character varying, 'Customer'::character varying])::text[])))
);
CREATE UNIQUE INDEX users_email_key ON public.users USING btree (email);
CREATE UNIQUE INDEX users_username_key ON public.users USING btree (username);

CREATE TABLE publishers(
    created_at timestamp without time zone,
    id SERIAL NOT NULL,
    address varchar(255),
    contact_email varchar(255),
    contact_phone varchar(255),
    name varchar(255) NOT NULL,
    website varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE telescopes(
    aperture numeric(38,2),
    available boolean,
    price_per_day numeric(38,2),
    created_at timestamp without time zone,
    id SERIAL NOT NULL,
    publisher_id bigint,
    brand varchar(255),
    description varchar(255),
    name varchar(255) NOT NULL,
    "type" varchar(255),
    PRIMARY KEY(id),
    CONSTRAINT FKpv3iop6hssy51ddocd4kvnfbf FOREIGN key(publisher_id) REFERENCES publishers(id)
);

CREATE TABLE accessories(
    price numeric(38,2),
    stock_quantity integer,
    created_at timestamp without time zone,
    id SERIAL NOT NULL,
    publisher_id bigint,
    category varchar(255),
    description varchar(255),
    name varchar(255) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT FKj8f59983ia0n8oc1unu3x1lry FOREIGN key(publisher_id) REFERENCES publishers(id)
);

CREATE TABLE reviews(
    rating integer NOT NULL,
    accessory_id bigint,
    created_at timestamp without time zone,
    id SERIAL NOT NULL,
    telescope_id bigint,
    user_id bigint NOT NULL,
    review_text varchar(255),
    PRIMARY KEY(id),
    CONSTRAINT FKiah7rxxnsghj268qtfi09d5q8 FOREIGN key(accessory_id) REFERENCES accessories(id),
    CONSTRAINT FK6420wpjl9k92d1jo1rvvmn6bn FOREIGN key(telescope_id) REFERENCES telescopes(id),
    CONSTRAINT FKryv9ovxoh6df2nlc5a5uog2ve FOREIGN key(user_id) REFERENCES users(id)
);

CREATE TABLE images(
    accessory_id bigint,
    id SERIAL NOT NULL,
    telescope_id bigint,
    uploaded_at timestamp without time zone,
    image_url varchar(255) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT FKekwgemu4b87mr7np395nyc7fl FOREIGN key(accessory_id) REFERENCES accessories(id),
    CONSTRAINT FK4qld06cmwuvits0rexja20mo2 FOREIGN key(telescope_id) REFERENCES telescopes(id)
);

CREATE TABLE reservations(
    end_date date,
    start_date date,
    total_cost numeric(38,2),
    created_at timestamp without time zone,
    id SERIAL NOT NULL,
    telescope_id bigint NOT NULL,
    user_id bigint NOT NULL,
    notes varchar(255),
    payment_status varchar(255),
    status varchar(255),
    PRIMARY KEY(id),
    CONSTRAINT FK7hx71bxcesepq05ynjqm3uqyh FOREIGN key(telescope_id) REFERENCES telescopes(id),
    CONSTRAINT FKbqc054ubmoqj00bl3mey759qx FOREIGN key(user_id) REFERENCES users(id),
    CONSTRAINT reservations_payment_status_check CHECK (((payment_status)::text = ANY ((ARRAY['Pending'::character varying, 'Completed'::character varying, 'Failed'::character varying])::text[]))),
    CONSTRAINT reservations_status_check CHECK (((status)::text = ANY ((ARRAY['Pending'::character varying, 'Confirmed'::character varying, 'Cancelled'::character varying])::text[])))
);

CREATE TABLE orders(
    total_price numeric(38,2),
    created_at timestamp without time zone,
    id SERIAL NOT NULL,
    user_id bigint NOT NULL,
    payment_status varchar(255),
    status varchar(255),
    PRIMARY KEY(id),
    CONSTRAINT FKitd0598xtxfyrro0df4ey8kdd FOREIGN key(user_id) REFERENCES users(id),
    CONSTRAINT orders_payment_status_check CHECK (((payment_status)::text = ANY ((ARRAY['Pending'::character varying, 'Completed'::character varying, 'Failed'::character varying])::text[]))),
    CONSTRAINT orders_status_check CHECK (((status)::text = ANY ((ARRAY['Pending'::character varying, 'Completed'::character varying, 'Cancelled'::character varying])::text[])))
);

CREATE TABLE order_items(
    quantity integer,
    accessory_id bigint,
    id SERIAL NOT NULL,
    order_id bigint NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT FKa9l6dumawlsofxcejacxj6658 FOREIGN key(accessory_id) REFERENCES accessories(id),
    CONSTRAINT FK6gg8woymk43fp55jtjlgkd1eh FOREIGN key(order_id) REFERENCES orders(id)
);

CREATE TABLE user_favorites(
    added_at timestamp without time zone,
    telescope_id bigint NOT NULL,
    user_id bigint NOT NULL,
    PRIMARY KEY(telescope_id,user_id),
    CONSTRAINT FK8lnu4t3ced9at5oe55untl86d FOREIGN key(telescope_id) REFERENCES telescopes(id),
    CONSTRAINT FKgj3aynr9rw2tgdd0c7gpcsjxe FOREIGN key(user_id) REFERENCES users(id)
);
