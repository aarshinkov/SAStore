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
import java.util.*;

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
    public ObjCollection<UserEntity> getUsers(Integer page, Integer limit, UserFilter userFilter) {


        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             CallableStatement cstmt = conn.prepareCall("{? = call get_users(?, ?, ?, ?, ?, ?)}");
             CallableStatement cstmtRoles = conn.prepareCall("{? = call get_user_roles(?)}")) {

            conn.setAutoCommit(false);

            cstmt.setInt(1, page);
            cstmt.setInt(2, limit);

            if (StringUtils.isEmpty(userFilter.getEmail())) {
                cstmt.setString(3, null);
            } else {
                cstmt.setString(3, userFilter.getEmail());
            }

            if (StringUtils.isEmpty(userFilter.getFirstName())) {
                cstmt.setString(4, null);
            } else {
                cstmt.setString(4, userFilter.getFirstName());
            }

            if (StringUtils.isEmpty(userFilter.getLastName())) {
                cstmt.setString(5, null);
            } else {
                cstmt.setString(5, userFilter.getLastName());
            }

            cstmt.registerOutParameter(6, Types.BIGINT);
            cstmt.registerOutParameter(7, Types.REF_CURSOR);

            cstmt.execute();

            Long globalCount = cstmt.getLong(6);
            ResultSet rset = (ResultSet) cstmt.getObject(7);

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

            long collectionCount = collection.getCollection().size();

            int start = (page - 1) * limit + 1;
            int end = start + collection.getCollection().size() - 1;

            Page pageWrapper = new PageImpl();
            pageWrapper.setCurrentPage(page);
            pageWrapper.setMaxElementsPerPage(limit);
            pageWrapper.setStartPage(start);
            pageWrapper.setEndPage(end);
            pageWrapper.setLocalTotalElements(collectionCount);
            pageWrapper.setGlobalTotalElements(globalCount);

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
