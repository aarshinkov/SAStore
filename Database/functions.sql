-- Get users
CREATE OR REPLACE FUNCTION get_users(ip_page_number IN int, ip_user_count IN int, ip_email IN varchar, ip_first_name IN varchar, ip_last_name IN varchar, op_all_rows OUT bigint, saCursor OUT refcursor) AS $$
DECLARE
	v_sql varchar;
	v_where varchar;
	v_sql_count varchar;
BEGIN
	v_sql := 'SELECT * FROM users u';
	
	op_all_rows := get_users_count();
	
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