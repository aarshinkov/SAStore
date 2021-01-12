package com.sastore.domain;

import com.sastore.web.entities.RoleEntity;
import com.sastore.web.enums.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RolesTests {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<String> rolesString = new ArrayList<>();
    private List<RoleEntity> rolesObj = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        rolesString.add("ADMIN");
        rolesString.add("SALES");
        rolesString.add("USER");

        rolesObj.add(new RoleEntity("ADMIN"));
        rolesObj.add(new RoleEntity("SALES"));
        rolesObj.add(new RoleEntity("USER"));
    }

    @Test
    public void test_enum() {
        try {
            Roles role = Roles.valueOf("ADMIN");

            log.debug(role + "");
        } catch (IllegalArgumentException iar) {
            log.error("Error getting role", iar);
        }
    }

    @Test
    public void test_whenOneRole_OutputsTheOthersString() {
        List<String> userRoles = new ArrayList<>();
        userRoles.add("USER");

        List<String> unassignedRoles = new ArrayList<>();

        for (String role : rolesString) {
            if (!userRoles.contains(role)) {
                unassignedRoles.add(role);
            }
        }

        assertEquals(2, unassignedRoles.size());
        assertEquals("ADMIN", unassignedRoles.get(0));
        assertEquals("SALES", unassignedRoles.get(1));
    }

    @Test
    public void test_whenTwoRoles_OutputsTheOthersString() {
        List<String> userRoles = new ArrayList<>();
        userRoles.add("ADMIN");
        userRoles.add("USER");

        List<String> unassignedRoles = new ArrayList<>();

        for (String role : rolesString) {
            if (!userRoles.contains(role)) {
                unassignedRoles.add(role);
            }
        }

        assertEquals(1, unassignedRoles.size());
        assertEquals("SALES", unassignedRoles.get(0));
    }

    @Test
    public void test_whenOneRole_OutputsTheOthers() {
        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(new RoleEntity("USER"));

        List<RoleEntity> unassignedRoles = new ArrayList<>();

        for (RoleEntity role : rolesObj) {
            if (!userRoles.contains(role)) {
                unassignedRoles.add(role);
            }
        }

        assertEquals(2, unassignedRoles.size());
    }

    @Test
    public void test_whenTwoRoles_OutputsTheOthers() {
        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(new RoleEntity("ADMIN"));
        userRoles.add(new RoleEntity("USER"));

        List<RoleEntity> unassignedRoles = new ArrayList<>();

        for (RoleEntity role : rolesObj) {
            if (!userRoles.contains(role)) {
                unassignedRoles.add(role);
            }
        }

        assertEquals(1, unassignedRoles.size());
    }

    @Test
    public void test_whenAllRoles_OutputsEmptyList() {
        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(new RoleEntity("ADMIN"));
        userRoles.add(new RoleEntity("USER"));
        userRoles.add(new RoleEntity("SALES"));

        List<RoleEntity> unassignedRoles = new ArrayList<>();

        for (RoleEntity role : rolesObj) {
            if (!userRoles.contains(role)) {
                unassignedRoles.add(role);
            }
        }

        assertEquals(0, unassignedRoles.size());
    }

    @Test
    public void test_whenRolesEquals_ReturnTrue() {
        RoleEntity role1 = new RoleEntity("ADMIN");
        RoleEntity role2 = new RoleEntity("ADMIN");

        assertTrue(role1.equals(role2));
    }
}
