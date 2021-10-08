package com.sastore.web.security;

import com.sastore.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Order of antmatchers -> specific ones first, then globals

    http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/login", "/signup").anonymous()
            .antMatchers("/admin/sales/**").hasAnyRole("ADMIN", "SALES")
            .antMatchers("/admin/users/**", "/admin/user/**").hasRole("ADMIN")
            .antMatchers("/admin/users/count/**").hasRole("ADMIN")
            .antMatchers("/admin/products/**", "/admin/product/**").hasAnyRole("ADMIN", "PRODUCTS")
            .antMatchers("/admin/orders/**", "/admin/order/**").hasAnyRole("ADMIN", "ORDERS")
            .antMatchers("/profile/orders", "/profile/favorites", "/profile/addresses").hasRole("USER")
            .antMatchers("/profile/**").authenticated()
            .antMatchers("/admin/dashboard").hasAnyRole("ADMIN", "SALES", "PRODUCTS")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/**").permitAll()
            .and()
            .formLogin()
            .loginProcessingUrl("/authentication")
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(authenticationSuccessHandler)
            .and()
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and()
            .httpBasic();
  }
}
