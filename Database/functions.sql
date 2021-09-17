-- Get users
CREATE OR REPLACE FUNCTION get_users(ip_page_number IN int, ip_user_count IN int, ip_user_id IN varchar, ip_email IN varchar, ip_is_active IN boolean, ip_first_name IN varchar, ip_last_name IN varchar, ip_rolename IN varchar, ip_order IN varchar, op_all_users OUT bigint, op_all_rows OUT bigint, saCursor OUT refcursor) AS $$
DECLARE
	v_sql varchar;
	v_where varchar;
	v_sql_count varchar;
	v_has_previous int := 0;
BEGIN
	v_sql := 'SELECT u.* FROM users u ';
	v_sql_count := 'SELECT count(u.*) FROM users u ';
	v_where := 'WHERE ';
	
	IF ip_rolename IS NOT NULL THEN
		v_sql := v_sql || 'JOIN user_roles ur ON u.user_id = ur.user_id ';
		v_sql_count := v_sql_count || 'JOIN user_roles ur ON u.user_id = ur.user_id ';
	END IF;

	IF ip_user_id IS NOT NULL THEN
		v_where := v_where || 'u.user_id like ''%' || ip_user_id || '%'' ';
		v_has_previous := 1;
	END IF;
	
	IF ip_email IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND u.email like ''%' || ip_email || '%'' ';
		ELSE
			v_where := v_where || 'u.email like ''%' || ip_email || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF ip_is_active IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND u.is_active = ' || ip_is_active || ' ';
		ELSE
			v_where := v_where || 'u.is_active = ' || ip_is_active || ' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF ip_first_name IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND lower(u.first_name) like ''%' || lower(ip_first_name) || '%'' ';
		ELSE
			v_where := v_where || 'lower(u.first_name) like ''%' || lower(ip_first_name) || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF ip_last_name IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND lower(u.last_name) like ''%' || lower(ip_last_name) || '%'' ';
		ELSE
			v_where := v_where || 'lower(u.last_name) like ''%' || lower(ip_last_name) || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF ip_rolename IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND ur.rolename like ''%' || upper(ip_rolename) || '%'' ';
		ELSE
			v_where := v_where || 'ur.rolename like ''%' || upper(ip_rolename) || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF v_has_previous <> 0 THEN
		v_sql := v_sql || v_where;
		v_sql_count := v_sql_count || v_where;
	END IF;
	
	EXECUTE v_sql_count INTO op_all_rows;
	
	v_sql := v_sql || 'ORDER BY u.created_on ' || ip_order || ' LIMIT ' || ip_user_count || ' OFFSET ' || (ip_page_number - 1) * ip_user_count;
	
	op_all_users := get_users_count();
	
	OPEN saCursor FOR EXECUTE v_sql;
END;
$$ LANGUAGE plpgsql;

-- Get user roles
CREATE OR REPLACE FUNCTION get_user_roles(identifier IN varchar, saCursor OUT refcursor) RETURNS refcursor AS $$
BEGIN
	OPEN saCursor FOR 
	SELECT r.rolename 
	FROM users u 
	JOIN user_roles ur on u.user_id = ur.user_id 
	JOIN roles r on ur.rolename = r.rolename
	WHERE u.email = identifier
	OR u.user_id = identifier;
END;
$$ LANGUAGE plpgsql;

-- Get users count
CREATE OR REPLACE FUNCTION get_users_count(op_all_rows OUT bigint) RETURNS BIGINT AS $$
BEGIN
	SELECT count(*) INTO op_all_rows FROM users u;
END;
$$ LANGUAGE plpgsql;

-- Get products
CREATE OR REPLACE FUNCTION get_products(ip_page_number IN int, ip_product_count IN int, ip_product_id IN bigint, ip_title IN varchar,op_all_products OUT bigint, op_all_rows OUT bigint, saCursor OUT refcursor) AS $$
DECLARE
	v_sql varchar;
	v_where varchar;
	v_sql_count varchar;
	v_has_previous int := 0;
BEGIN
	v_sql := 'SELECT * FROM products p ';
	v_sql_count := 'SELECT count(*) FROM products p ';
	v_where := 'WHERE ';

	IF ip_product_id IS NOT NULL THEN
		v_where := v_where || 'product_id = ' || ip_product_id || ' ';
		v_has_previous := 1;
	END IF;
	
	IF ip_title IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND lower(title) like ''%' || ip_title || '%'' ';
		ELSE
			v_where := v_where || 'lower(title) like ''%' || ip_title || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF v_has_previous <> 0 THEN
		v_sql := v_sql || v_where;
		v_sql_count := v_sql_count || v_where;
	END IF;
	
	EXECUTE v_sql_count INTO op_all_rows;
	
	v_sql := v_sql || 'ORDER BY added_on DESC LIMIT ' || ip_product_count || ' OFFSET ' || (ip_page_number - 1) * ip_product_count;
	
	op_all_products := get_products_count();
	
	OPEN saCursor FOR EXECUTE v_sql;
END;
$$ LANGUAGE plpgsql;

-- Get products count
CREATE OR REPLACE FUNCTION get_products_count(op_all_rows OUT bigint) RETURNS BIGINT AS $$
BEGIN
	SELECT count(*) INTO op_all_rows FROM products u;
END;
$$ LANGUAGE plpgsql;