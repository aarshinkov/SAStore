package com.sastore.web;

import com.sastore.web.beans.PasswordValidator;
import com.sastore.web.beans.Version;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@SpringBootApplication
public class SaStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaStoreApplication.class, args);
    }

    @Bean
    public Version version() {
        return new Version();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator();
    }

//    @Bean
//    public SecurityChecks securityChecks() {
//        return new SecurityChecks();
//    }
}
