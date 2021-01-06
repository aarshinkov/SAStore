CREATE TABLE users(
	user_id varchar(100) not null primary key,
	email varchar(200) not null unique,
	password varchar(100) not null,
	first_name varchar(100) not null,
	last_name varchar(100),
    is_active boolean not null default false,
	created_on timestamp not null default NOW(),
	edited_on timestamp
);

CREATE TABLE roles(
	rolename varchar(50) not null primary key
);

INSERT INTO roles (rolename) VALUES ('ADMIN');
INSERT INTO roles (rolename) VALUES ('USER');

CREATE SEQUENCE public.s_user_roles
	INCREMENT 1
	START 1;
	
ALTER SEQUENCE public.s_user_roles
	OWNER TO sastore_user;

CREATE TABLE user_roles(
	user_role_id int not null primary key default nextval('s_user_roles'),
	user_id varchar(100) not null references users(user_id),
	rolename varchar(50) not null references roles(rolename),
	created_on timestamp not null default NOW()
);