-- Get users
CREATE OR REPLACE FUNCTION get_users(ip_page_number IN int, ip_user_count IN int, ip_user_id IN varchar, ip_email IN varchar, ip_first_name IN varchar, ip_last_name IN varchar, op_all_users OUT bigint, op_all_rows OUT bigint, saCursor OUT refcursor) AS $$
DECLARE
	v_sql varchar;
	v_where varchar;
	v_sql_count varchar;
	v_has_previous int := 0;
BEGIN
	v_sql := 'SELECT * FROM users u ';
	v_sql_count := 'SELECT count(*) FROM user u ';
	v_where := 'WHERE ';

	IF ip_user_id IS NOT NULL THEN
		v_where := v_where || 'user_id like ''%' || ip_user_id || '%'' ';
		v_has_previous := 1;
	END IF;
	
	IF ip_email IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND email like ''%' || ip_email || '%'' ';
		ELSE
			v_where := v_where || 'email like ''%' || ip_email || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF ip_first_name IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND first_name like ''%' || ip_first_name || '%'' ';
		ELSE
			v_where := v_where || 'first_name like ''%' || ip_first_name || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF ip_last_name IS NOT NULL THEN
		IF v_has_previous = 1 THEN
			v_where := v_where || 'AND last_name like ''%' || ip_last_name || '%'' ';
		ELSE
			v_where := v_where || 'last_name like ''%' || ip_last_name || '%'' ';
			v_has_previous := 1;
		END IF;
	END IF;
	
	IF v_has_previous <> 0 THEN
		v_sql := v_sql || v_where;
		v_sql_count := v_sql || v_where;
	END IF;
	
	EXECUTE IMMEDIATE v_sql_count INTO op_all_rows;
	
	v_sql := v_sql || 'ORDER BY created_on DESC LIMIT ' || ip_user_count || ' OFFSET ' || (ip_page_number - 1) * ip_user_count;
	
	INSERT INTO logs (log_text) VALUES (v_sql);
	
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