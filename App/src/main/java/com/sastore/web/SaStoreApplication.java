package com.sastore.web;

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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
