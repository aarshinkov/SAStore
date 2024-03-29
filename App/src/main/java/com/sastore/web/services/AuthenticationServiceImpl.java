package com.sastore.web.services;

import com.sastore.web.domain.AuthSuccessResponse;
import com.sastore.web.domain.AuthenticationResponse;
import com.sastore.web.domain.AuthenticationResponses;
import com.sastore.web.domain.NameDomain;
import com.sastore.web.entities.AddressEntity;
import com.sastore.web.entities.CartEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.repositories.UsersRepository;
import com.sastore.web.security.LoggedUser;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import com.sastore.web.repositories.CartsRepository;
import eu.bitwalker.useragentutils.UserAgent;
import java.sql.Timestamp;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final RequestCache requestCache = new HttpSessionRequestCache();

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private FavoriteService favoriteService;

  @Autowired
  private CartsRepository cartsRepository;

  @Autowired
  private AddressService addressService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public AuthenticationResponse authenticate(String username, String password) {
    AuthenticationResponse response = new AuthenticationResponse();

    UserEntity foundUser = usersRepository.findByEmail(username);

    if (foundUser == null) {
      response.setResponse(AuthenticationResponses.USER_INVALID);
      return response;
    }

    // Password does NOT match
    if (!passwordEncoder.matches(password, foundUser.getPassword())) {
      response.setResponse(AuthenticationResponses.BAD_CREDENTIALS);
      return response;
    }

    response.setResponse(AuthenticationResponses.OK);

    return response;
  }

  @Override
  public AuthSuccessResponse onAuthSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    log.debug("Authentication successful.");

    HttpSession session = request.getSession();

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    LoggedUser loggedUser = (LoggedUser) auth.getPrincipal();
    loggedUser.setLoggedOn(new Timestamp(System.currentTimeMillis()));
//    loggedUser.setUserAgent(request.getHeader("User-Agent"));
    UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    loggedUser.setUserAgent(userAgent);

    String email = authentication.getName();

    UserEntity user = usersRepository.findByEmail(email);
    CartEntity cart = cartsRepository.findByUserUserId(user.getUserId());

    if (cart == null) {
      CartEntity newCart = new CartEntity();
      newCart.setCartId(UUID.randomUUID().toString());
      newCart.setUser(user);

      cartsRepository.save(newCart);
    }

    NameDomain names = NameDomain.builder().firstName(user.getFirstName()).lastName(user.getLastName()).build();

    session.setAttribute("user", names);
    session.setAttribute("loggedUser", loggedUser);
//    session.setAttribute("userId", user.getUserId());
//    session.setAttribute("email", email);
//    session.setAttribute("avatar", user.getAvatar());
    session.setAttribute("roles", user.getRoles());
//    session.setAttribute("createdOn", user.getCreatedOn());

    session.setAttribute("favoritesCount", favoriteService.getUserFavoritesCount(loggedUser.getUserId()));

    AddressEntity mainAddress = addressService.getUserMainAddress(user.getUserId());
    if (mainAddress != null) {
      session.setAttribute("mainAddressCountry", mainAddress.getCountry());
      session.setAttribute("mainAddressCity", mainAddress.getCity());
    }

    SavedRequest savedRequest = requestCache.getRequest(request, response);

    AuthSuccessResponse authResponse = new AuthSuccessResponse();
    authResponse.setRedirectUrl("/");

    if (savedRequest == null) {
      log.debug("Redirecting to url: " + authResponse.getRedirectUrl());
      return authResponse;
    }

    // Use the DefaultSavedRequest URL
    if (savedRequest.getRedirectUrl() != null) {
      if (!savedRequest.getRedirectUrl().trim().isEmpty()) {
        authResponse.setRedirectUrl(savedRequest.getRedirectUrl());
      }
    }

    return authResponse;
  }
}
