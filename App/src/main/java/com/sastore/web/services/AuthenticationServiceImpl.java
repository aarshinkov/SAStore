package com.sastore.web.services;

import com.sastore.web.domain.AuthSuccessResponse;
import com.sastore.web.domain.AuthenticationResponse;
import com.sastore.web.domain.AuthenticationResponses;
import com.sastore.web.domain.NameDomain;
import com.sastore.web.entities.AddressEntity;
import com.sastore.web.entities.BasketEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.repositories.BasketsRepository;
import com.sastore.web.repositories.UsersRepository;
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
  private BasketsRepository basketsRepository;

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

    String email = authentication.getName();

    UserEntity user = usersRepository.findByEmail(email);
    BasketEntity basket = basketsRepository.findByUserUserId(user.getUserId());

    if (basket == null) {
      BasketEntity newBasket = new BasketEntity();
      newBasket.setBasketId(UUID.randomUUID().toString());
      newBasket.setUser(user);

      basketsRepository.save(newBasket);
    }

    NameDomain names = NameDomain.builder().firstName(user.getFirstName()).lastName(user.getLastName()).build();

    session.setAttribute("user", names);
    session.setAttribute("userId", user.getUserId());
    session.setAttribute("email", email);
    session.setAttribute("avatar", user.getAvatar());
    session.setAttribute("roles", user.getRoles());
    session.setAttribute("createdOn", user.getCreatedOn());

    AddressEntity mainAddress = addressService.getUserMainAddress(user.getUserId());
    if (mainAddress != null) {
      session.setAttribute("mainAddressCountry", mainAddress.getCountry());
      session.setAttribute("mainAddressCity", mainAddress.getCity());
    }

    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest == null) {

      return null;
    }

    // Use the DefaultSavedRequest URL
    String targetUrl = "/";

    if (savedRequest.getRedirectUrl() != null) {
      if (!savedRequest.getRedirectUrl().trim().isEmpty()) {
        targetUrl = savedRequest.getRedirectUrl();
      }
    }
    log.debug("Redirecting to url: " + targetUrl);
    AuthSuccessResponse authResponse = new AuthSuccessResponse();
    authResponse.setRedirectUrl(targetUrl);
    return authResponse;
  }
}
