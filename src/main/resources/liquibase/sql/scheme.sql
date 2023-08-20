-- liquibase formatted sql

-- changeset akmeevd:1
create table mailings(
id bigserial primary key,
type varchar(11),
status varchar(10),
reg_date timestamp,
receiving_date timestamp
);

create table posts(
index serial primary key,
name varchar(50),
address varchar(50)
);

create table recipients(
id bigserial primary key,
name varchar(50),
address varchar(50),
index integer,
mailing_id bigint references mailings (id)
);

alter table mailings add column recipient_id bigint references recipients (id);

create table routings(
id bigserial primary key,
post_from_index integer references posts (index),
intermediate_post_index integer references posts (index),
post_to_index integer references posts (index),
start timestamp,
departure_time_from_inter_post timestamp,
arrival_time_to_inter_post timestamp,
arrival timestamp
);

alter table mailings add column routing_id bigint references routings (id);

alter table posts add column routing_id bigint references routings (id);

create table routings_mailing(
routing_id bigint references routings (id),
mailing_id bigint references mailings (id)
);

create table posts_routing(
post_index integer references posts (index),
routing_id bigint references routings (id)
);
