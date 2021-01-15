package com.sastore.web.dao;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.collections.UsersCollection;
import com.sastore.web.entities.RoleEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.enums.Order;
import com.sastore.web.filters.UserFilter;
import com.sastore.web.utils.Page;
import com.sastore.web.utils.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public class UsersDaoImpl implements UsersDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ObjCollection<UserEntity> getUsers(Integer page, Integer limit, UserFilter filter) {

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             CallableStatement cstmt = conn.prepareCall("{? = call get_users(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
             CallableStatement cstmtRoles = conn.prepareCall("{? = call get_user_roles(?)}")) {

            conn.setAutoCommit(false);

            cstmt.setInt(1, page);
            cstmt.setInt(2, limit);

            if (StringUtils.isEmpty(filter.getUserId())) {
                cstmt.setString(3, null);
            } else {
                cstmt.setString(3, filter.getUserId());
            }

            if (StringUtils.isEmpty(filter.getEmail())) {
                cstmt.setString(4, null);
            } else {
                cstmt.setString(4, filter.getEmail());
            }

            if (filter.getIsActive() == null) {
                cstmt.setNull(5, Types.BOOLEAN);
            } else {
                cstmt.setBoolean(5, filter.getIsActive());
            }

            if (StringUtils.isEmpty(filter.getFirstName())) {
                cstmt.setString(6, null);
            } else {
                cstmt.setString(6, filter.getFirstName());
            }

            if (StringUtils.isEmpty(filter.getLastName())) {
                cstmt.setString(7, null);
            } else {
                cstmt.setString(7, filter.getLastName());
            }

            if (StringUtils.isEmpty(filter.getRole())) {
                cstmt.setString(8, null);
            } else {
                cstmt.setString(8, filter.getRole());
            }

            if (StringUtils.isEmpty(filter.getOrder())) {
                cstmt.setString(9, Order.DESCENDING.getOrder());
            } else {
                if (filter.getOrder().equals(Order.ASCENDING.getOrder())) {
                    cstmt.setString(9, Order.ASCENDING.getOrder());
                } else {
                    cstmt.setString(9, Order.DESCENDING.getOrder());
                }
            }

            cstmt.registerOutParameter(10, Types.BIGINT);
            cstmt.registerOutParameter(11, Types.BIGINT);
            cstmt.registerOutParameter(12, Types.REF_CURSOR);

            cstmt.execute();

            Long total = cstmt.getLong(10);
            Long noPagedTotal = cstmt.getLong(11);
            ResultSet rset = (ResultSet) cstmt.getObject(12);

            ObjCollection<UserEntity> collection = new UsersCollection<>();

            while (rset.next()) {
                UserEntity user = new UserEntity();
                user.setUserId(rset.getString("user_id"));
                user.setEmail(rset.getString("email"));
                user.setFirstName(rset.getString("first_name"));
                user.setLastName(rset.getString("last_name"));
                user.setIsActive(rset.getBoolean("is_active"));
                user.setCreatedOn(rset.getTimestamp("created_on"));
                user.setEditedOn(rset.getTimestamp("edited_on"));

                cstmtRoles.registerOutParameter(1, Types.REF_CURSOR);
                cstmtRoles.setString(2, user.getEmail());

                cstmtRoles.execute();

                ResultSet rsetRoles = (ResultSet) cstmtRoles.getObject(1);

                List<RoleEntity> roles = new ArrayList<>();

                while (rsetRoles.next()) {

                    RoleEntity role = new RoleEntity();
                    role.setRolename(rsetRoles.getString("rolename"));

                    roles.add(role);
                }

                user.setRoles(roles);

                collection.getCollection().add(user);
            }

            long pagedTotal = collection.getCollection().size();

            int start = (page - 1) * limit + 1;
            int end = start + collection.getCollection().size() - 1;

            Page pageWrapper = new PageImpl();
            pageWrapper.setCurrentPage(page);
            pageWrapper.setMaxElementsPerPage(limit);
            pageWrapper.setStartPage(start);
            pageWrapper.setEndPage(end);
            pageWrapper.setNoPagedTotal(noPagedTotal);
            pageWrapper.setPagedTotal(pagedTotal);
            pageWrapper.setTotal(total);

            collection.setPage(pageWrapper);

            conn.commit();

            return collection;
        } catch (Exception e) {
            log.error("Error getting users!", e);
        }

        return null;
    }

    @Override
    public Long getUsersCountByRole(String rolename) {

        String sql = "SELECT COUNT(*) FROM user_roles WHERE roleName = ?";

        return jdbcTemplate.queryForObject(sql, Long.class, rolename);
    }
}
