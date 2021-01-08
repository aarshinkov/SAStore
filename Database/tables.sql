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

INSERT INTO users VALUES ('36c0d878-5504-4ba7-9379-a0d38f7b31fe', 'admin@sastore.com', '$2a$12$Hr1XAgIGOmQ.KRwxz5dvK.HVVVSXQ/ceMTljbus1rcW4qgy.ax5.K', 'Админ', null, true, NOW(), null);

CREATE TABLE roles(
	rolename varchar(50) not null primary key
);

INSERT INTO roles (rolename) VALUES ('ADMIN');
INSERT INTO roles (rolename) VALUES ('USER');
INSERT INTO roles (rolename) VALUES ('SALES');

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

INSERT INTO user_roles (user_id, rolename) VALUES ('36c0d878-5504-4ba7-9379-a0d38f7b31fe', 'ADMIN');