package com.sastore.web.beans;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class PasswordValidatorTests {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private PasswordValidator passwordValidator;

    @Before
    public void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    public void Test_IsPasswordValid_ReturnFalse() {
        assertFalse(passwordValidator.isPasswordValid("Test"));
        assertFalse(passwordValidator.isPasswordValid("Test1"));
        assertFalse(passwordValidator.isPasswordValid("1254"));
        assertFalse(passwordValidator.isPasswordValid("EW#5I934"));
        assertFalse(passwordValidator.isPasswordValid("test!556"));
        assertFalse(passwordValidator.isPasswordValid(""));
        assertFalse(passwordValidator.isPasswordValid("#e@wf5"));
    }

    @Test
    public void Test_IsPasswordValid_ReturnTrue() {
        assertTrue(passwordValidator.isPasswordValid("Test-1234"));
        assertTrue(passwordValidator.isPasswordValid("@wE32"));
        assertTrue(passwordValidator.isPasswordValid("%LmO5kff"));
        assertTrue(passwordValidator.isPasswordValid("T3$%4eWQA"));
    }
}
