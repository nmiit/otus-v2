create table cas (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null unique,
    password varchar(255) not null,
    email varchar(255) unique,
    profile_id bigint unique,
    created_at timestamp(6) with time zone default now() not null,
    updated_at timestamp(6) with time zone default now() not null,
    created_by varchar(50) default 'app',
    updated_by varchar(50) default 'app'
);
create table cas_profile (
    id BIGSERIAL PRIMARY KEY,
    full_name varchar(255) unique,
    phone varchar(255) unique,
    address_id bigint unique
);
create table address (
    id BIGSERIAL PRIMARY KEY,
    city varchar(255) not null default '',
    street varchar(255) not null default '',
    house varchar(255) not null default ''
);
create table cas_role (
    id BIGSERIAL PRIMARY KEY,
    role_name varchar(255) unique
);
create table cas_roles (
    cas_id bigint not null,
    roles_id bigint not null
);