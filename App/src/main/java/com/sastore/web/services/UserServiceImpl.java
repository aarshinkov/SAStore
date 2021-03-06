package com.sastore.web.services;

import com.sastore.web.collections.ObjCollection;
import com.sastore.web.collections.UsersCollection;
import com.sastore.web.entities.RoleEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.enums.Roles;
import com.sastore.web.filters.UserFilter;
import com.sastore.web.models.SignupModel;
import com.sastore.web.repositories.RolesRepository;
import com.sastore.web.repositories.UsersRepository;
import com.sastore.web.utils.Page;
import com.sastore.web.utils.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ObjCollection<UserEntity> getUsers(Integer page, Integer limit, UserFilter filter) {

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             CallableStatement cstmt = conn.prepareCall("{? = call get_users(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
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

            cstmt.registerOutParameter(8, Types.BIGINT);
            cstmt.registerOutParameter(9, Types.BIGINT);
            cstmt.registerOutParameter(10, Types.REF_CURSOR);

            cstmt.execute();

            Long total = cstmt.getLong(8);
            Long noPagedTotal = cstmt.getLong(9);
            ResultSet rset = (ResultSet) cstmt.getObject(10);

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
    public UserEntity getUserByEmail(String email) {

        return usersRepository.findByEmail(email);
    }

    @Override
    public UserEntity getUserByUserId(String userId) {
        return usersRepository.findByUserId(userId);
    }

    @Override
    public UserEntity createUser(SignupModel signupModel) {

        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName(signupModel.getFirstName());
        user.setLastName(signupModel.getLastName());
        user.setEmail(signupModel.getEmail());

        String encodedPassword = passwordEncoder.encode(signupModel.getPassword());

        user.setPassword(encodedPassword);

        List<RoleEntity> roles = new ArrayList<>();

        RoleEntity userRole = rolesRepository.findByRolename(Roles.USER.getRole());

        roles.add(userRole);

        user.setRoles(roles);

        return usersRepository.save(user);
    }

    @Override
    public Long getUsersCountByRole(String rolename) {

        String sql = "SELECT COUNT(*) FROM user_roles WHERE roleName = ?";

        return jdbcTemplate.queryForObject(sql, Long.class, rolename);
    }

    @Override
    public UserEntity addRole(String userId, Roles role) throws Exception {
        UserEntity user = usersRepository.findByUserId(userId);

        if (user == null) {
            throw new Exception("User with ID " + userId + " not found!");
        }

        RoleEntity newRole = rolesRepository.findByRolename(role.getRole().toUpperCase());

        user.getRoles().add(newRole);

        return usersRepository.save(user);
    }

    @Override
    public UserEntity removeRole(String userId, Roles role) throws Exception {
        UserEntity user = usersRepository.findByUserId(userId);

        if (user == null) {
            throw new Exception("User with ID " + userId + " not found!");
        }

        RoleEntity storedRole = rolesRepository.findByRolename(role.getRole().toUpperCase());

        List<RoleEntity> userRoles = user.getRoles();

        for (int i = 0; i < userRoles.size(); i++) {
            if (userRoles.get(i).getRolename().equals(storedRole.getRolename())) {
                userRoles.remove(i);
            }
        }

        user.setRoles(userRoles);

        return usersRepository.save(user);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        UserEntity storedUser = usersRepository.findByEmail(email);

        return storedUser != null;
    }

    @Override
    public boolean isPasswordMatch(String userId, String password) {

        UserEntity storedUser = usersRepository.findByUserId(userId);

        return passwordEncoder.matches(password, storedUser.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return user;
    }
}
