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
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/signup").anonymous()
                .antMatchers("/dashboard").hasAnyRole("ADMIN", "SALES")
                .antMatchers("/account/orders", "/account/wishlist", "/account/addresses").hasRole("USER")
                .antMatchers("/account/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/sales/**").hasAnyRole("ADMIN", "SALES")
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().logoutUrl("/logout")
                .and().csrf().disable();
    }
}
