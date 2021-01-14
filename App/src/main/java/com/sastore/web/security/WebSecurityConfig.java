package com.sastore.web.security;

import com.sastore.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Order of antmatchers -> specific ones first, then globals

        http.authorizeRequests()
                .antMatchers("/login", "/signup").anonymous()
                .antMatchers("/admin/sales/**").hasAnyRole("ADMIN", "SALES")
                .antMatchers("/admin/users/**").hasRole("ADMIN")
                .antMatchers("/admin/user/**").hasRole("ADMIN")
                .antMatchers("/admin/users/count/**").hasRole("ADMIN")
                .antMatchers("/admin/products/**").hasAnyRole("ADMIN", "PRODUCTS")
                .antMatchers("/admin/product/**").hasAnyRole("ADMIN", "PRODUCTS")
                .antMatchers("/account/orders", "/account/wishlist", "/account/addresses").hasRole("USER")
                .antMatchers("/account/**").authenticated()
                .antMatchers("/admin/dashboard").hasAnyRole("ADMIN", "SALES", "PRODUCTS")
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/dashboard").hasAnyRole("ADMIN", "SALES", "PRODUCTS")
//                .antMatchers("/account/orders", "/account/wishlist", "/account/addresses").hasRole("USER")
//                .antMatchers("/account/**").authenticated()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/sales/**").hasAnyRole("ADMIN", "SALES")
//                .antMatchers("/users/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("ADMIN")
//                .antMatchers("/count/**").hasRole("ADMIN")
//                .antMatchers("/admin/products/**").hasAnyRole("ADMIN", "PRODUCTS")
//                .antMatchers("/admin/product/**").hasAnyRole("ADMIN", "PRODUCTS")
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().logoutUrl("/logout")
                .and().csrf().disable();
    }
}
