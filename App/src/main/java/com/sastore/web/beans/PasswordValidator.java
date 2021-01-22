package com.sastore.web.beans;

/**
 * A class for password verification. It checks if a provided password meet the
 * requirements
 *
 * @author Atanas Yordanov Arhinkov
 * @since 1.0.0
 */
public class PasswordValidator {

    /**
     * String variable containing the digits
     */
    private final String DIGITS = "0123456789";

    /**
     * String variable containing the special symbols
     */
    private final String SPECIAL_SYMBOLS = "!@#$%^&*-_";

    /**
     * The method takes a password as an argument and checks if it complies with
     * the requirements
     *
     * @param password the password to be checked
     * @return true - if the password is valid; false - if it is not
     */
    public Boolean isPasswordValid(String password) {

        if (!hasSymbols(password, SPECIAL_SYMBOLS)) {
            return false;
        }

        if (!hasSymbols(password, DIGITS)) {
            return false;
        }

        return hasCases(password);
    }

    /**
     * Checks if the provided string has an uppercase character.
     *
     * <i>Uncomment the commented parts for lowercase check</i>
     *
     * @param password the password string to be checked
     * @return if the string contains an uppercase letter
     */
    private Boolean hasCases(final String password) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for (int i = 0; i < password.length(); i++) {
            ch = password.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a password string contains a character from a character
     * sequence
     *
     * @param password the password string to be checked
     * @param SYMBOLS the character sequence of the symbols to be checked from
     * @return true if the provided password as string contains at least one
     * symbol
     */
    private Boolean hasSymbols(final String password, final String SYMBOLS) {

        for (int i = 0; i < password.length(); i++) {
            for (int j = 0; j < SYMBOLS.length(); j++) {
                if (password.charAt(i) == SYMBOLS.charAt(j)) {
                    return true;
                }
            }
        }

        return false;
    }
}
