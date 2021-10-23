package com.sastore.web.security;

import com.sastore.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//  @Autowired
//  private AuthenticationProvider authProvider;
  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

//  @Autowired
//  private AuthenticationSuccessHandler authenticationSuccessHandler;
  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  @Autowired
  private SessionRegistry sessionRegistry;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    super.configure(auth);
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//    auth.authenticationProvider(authProvider);
  }

  @Bean(name = "authenticationManager")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
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
            //            .formLogin()
            //            .loginProcessingUrl("/authentication")
            //            .loginPage("/login")
            //            .usernameParameter("email")
            //            .passwordParameter("password")
            //            .successHandler(authenticationSuccessHandler)
            //            .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
            .and()
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and()
            .httpBasic();

    http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .invalidSessionUrl("/login")
            .enableSessionUrlRewriting(false)
            .maximumSessions(5).sessionRegistry(sessionRegistry);
  }

  /**
   * When this bean is registered the logged users list is updated.
   * <br><br>
   * You could see documentation for session registry.
   * <br><br>
   * I also updated the http.sessionManagement()... lines.
   *
   * @return new HttpSessionEventPublisher instance
   */
  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }
}
