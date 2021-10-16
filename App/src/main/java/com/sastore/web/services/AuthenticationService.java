package com.sastore.web.services;

import com.sastore.web.domain.AuthSuccessResponse;
import com.sastore.web.domain.AuthenticationResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public interface AuthenticationService {

  AuthenticationResponse authenticate(String username, String password);

  AuthSuccessResponse onAuthSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException;
}
