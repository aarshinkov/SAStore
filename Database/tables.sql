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

INSERT INTO users VALUES ('36c0d878-5504-4ba7-9379-a0d38f7b31fe', 'admin@sastore.com', '$2a$12$Hr1XAgIGOmQ.KRwxz5dvK.HVVVSXQ/ceMTljbus1rcW4qgy.ax5.K', 'Админ', null, 'user_default.png', true, NOW(), null);

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

CREATE TABLE product_variations(
	product_variation_id varchar(100) not null primary key,
	product_id varchar(100) not null references products(product_id),
	variation varchar(400) not null,
	is_color boolean not null default false,
	is_dimension boolean not null default false,
	assosiated_image varchar(500) references prod_images(image_id),
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
	entrance varchar(30),
	floor int,
	apartment_no int,
	user_id varchar(100) not null references users(user_id),
	is_main boolean not null default true,
	added_on timestamp not null default NOW(),
	edited_on timestamp
);

CREATE TABLE carts(
	cart_id varchar(100) not null primary key,
	user_id varchar(100) references users(user_id),
	session_id varchar(300),
	created_on timestamp not null default NOW(),
	--expires_on timestamp not null default (NOW() + interval '7 days'),
	is_active boolean not null default true
);

CREATE TABLE cart_products(
	cart_product_id varchar(100) not null primary key,
	cart_id varchar(100) not null references carts(cart_id),
	product_id varchar(100) not null references products(product_id),
	added_on timestamp not null default NOW()
);

CREATE TABLE favorites(
	favorite_id varchar(100) not null primary key,
	user_id varchar(100) references users(user_id),
	product_id varchar(100) not null references products(product_id),
	added_on timestamp not null default NOW()
);

CREATE TABLE order_addresses(
	order_address_id varchar(100) not null primary key,
	person_name varchar(300) not null,
	phone varchar(100) not null,
	country varchar(10) not null default 'bg',
	post_code varchar(50) not null,
	province varchar(100) not null default 'other',
	city varchar(100) not null,
	district varchar(100),
	street varchar(500) not null,
	street_no int not null,
	entrance varchar(30),
	floor int,
	apartment_no int
);

CREATE TABLE order_product_variations(
	order_product_variation_id varchar(100) not null primary key,
	product_id varchar(100) not null references products(product_id),
	variation varchar(400) not null,
	is_color boolean not null default false,
	is_dimension boolean not null default false,
	assosiated_image varchar(500) references prod_images(image_id)
);

-- 0 - Cash on delivery; 1 - Credit/Debit card; 2 - PayPal
-- Currently only 0 works
CREATE TABLE payment_types(
	payment_type_id int not null primary key,
	payment_type_descr varchar(100) not null,
	is_active boolean not null default false
);

INSERT INTO payment_types VALUES (0, 'Cash on delivery', true);
INSERT INTO payment_types VALUES (1, 'Credit/Debit card', false);
INSERT INTO payment_types VALUES (2, 'PayPal', false);

CREATE TABLE couriers(
	courier_id int not null primary key,
	courier_name varchar(100) not null,
	is_active boolean not null default false
);

INSERT INTO couriers VALUES (1, 'Econt Express', true);
INSERT INTO couriers VALUES (2, 'Speedy', true);
INSERT INTO couriers VALUES (3, 'Interlogistica', false);
INSERT INTO couriers VALUES (4, 'DHL', false);

CREATE TABLE order_statuses(
	order_status_id int not null primary key,
	order_status_descr varchar(100) not null
);

INSERT INTO order_statuses VALUES (1, 'Pending process');
INSERT INTO order_statuses VALUES (2, 'Processing');
INSERT INTO order_statuses VALUES (3, 'In courier');
INSERT INTO order_statuses VALUES (4, 'Finished');
INSERT INTO order_statuses VALUES (5, 'Canceled');

CREATE SEQUENCE public.s_orders
	INCREMENT 1
	START 1;
	
ALTER SEQUENCE public.s_orders
	OWNER TO sastore_user;

CREATE TABLE orders(
	order_id int not null primary key default nextval('s_orders'),
	user_id varchar(100) references users(user_id) on delete set null,
	delivery_address varchar(100) not null references order_addresses(order_address_id) on delete restrict,
	bill_address varchar(100) not null references order_addresses(order_address_id) on delete restrict,
	payment_type_id int not null references payment_types(payment_type_id) on delete restrict,
	courier_id int not null references couriers(courier_id) on delete restrict,
	status int not null default 1 references order_statuses(order_status_id) on delete restrict,
	created_on timestamp not null default NOW()
);

CREATE TABLE order_products(
	order_product_id varchar(100) not null primary key,
	order_id int not null references orders(order_id) on delete cascade,
	product_id varchar(100) not null references products(product_id) on delete cascade,
	order_product_variation_id varchar(100) not null references order_product_variations(order_product_variation_id) on delete restrict,
	quantity int not null default 1,
	price_per_unit numeric not null
);
