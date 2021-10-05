CREATE TABLE users(
	user_id varchar(100) not null primary key,
	email varchar(200) not null unique,
	password varchar(100) not null,
	first_name varchar(100) not null,
	last_name varchar(100),
	avatar varchar(200) not null default 'user_default.png',
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
INSERT INTO roles (rolename) VALUES ('PRODUCTS');
INSERT INTO roles (rolename) VALUES ('ORDERS');

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

CREATE SEQUENCE public.s_logs
	INCREMENT 1
	START 1;
	
ALTER SEQUENCE public.s_logs
	OWNER TO sastore_user;

CREATE TABLE logs(
	log_id int not null primary key default nextval('s_logs'),
	log_text text not null,
	created_on timestamp not null default NOW()
);
	
CREATE TABLE products(
	product_id varchar(100) not null primary key,
	title varchar(200) not null,
	price numeric not null,
	discount numeric not null default 0,
	avail_quant int not null default 1,
	views int not null default 0,
	description text not null,
	status int not null default 2,
	main_image varchar(500),
	added_on timestamp not null default NOW(),
	approved_on timestamp,
	edited_on timestamp
);

CREATE TABLE prod_images(
	image_id varchar(500) not null primary key,
	product_id varchar(100) not null references products(product_id),
	is_main boolean not null default false,
	created_on timestamp not null default NOW()
);

CREATE TABLE addresses(
	address_id varchar(100) not null primary key,
	person_name varchar(300) not null,
	phone varchar(100) not null,
	country varchar(10) not null default 'bg',
	post_code varchar(50) not null,
	province varchar(100) not null default 'other',
	city varchar(100) not null,
	district varchar(100),
	street varchar(500) not null,
	street_no int not null,
	enter varchar(30),
	floor int,
	apartment_no int,
	user_id varchar(100) not null references users(user_id),
	is_main boolean not null default true
);

CREATE TABLE baskets(
	basket_id varchar(100) not null primary key,
	user_id varchar(100) not null references users(user_id)
);

CREATE TABLE basket_products(
	basket_product_id varchar(100) not null primary key,
	basket_id varchar(100) not null references baskets(basket_id),
	product_id varchar(100) not null references products(product_id),
	added_on timestamp not null default NOW()
);