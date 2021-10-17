package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.beans.PasswordValidator;
import com.sastore.web.domain.AuthSuccessResponse;
import com.sastore.web.domain.AuthenticationResponse;
import com.sastore.web.domain.AuthenticationResponses;
import com.sastore.web.models.auth.LoginModel;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.models.auth.SignupModel;
import com.sastore.web.security.WebSecurityConfig;
import com.sastore.web.services.AuthenticationService;
import com.sastore.web.services.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContext;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0.
 */
@Controller
public class AuthenticationController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordValidator passwordValidator;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private AuthenticationProvider authProvider;

  @GetMapping("/login")
  public String prepareLogin(HttpServletRequest request, Model model) {

    model.addAttribute("login", new LoginModel());

    return "auth/login";
  }

  @PostMapping("/login")
  public String login(@ModelAttribute("login") @Valid LoginModel login, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res, RedirectAttributes redirectAttributes, Model model) throws IOException, ServletException {

    log.debug("Email: " + login.getEmail());

    if (bindingResult.hasErrors()) {
      return "auth/login";
    }

    AuthenticationResponse response = authenticationService.authenticate(login.getEmail(), login.getPassword());

    if (response.hasErrors()) {

      if (response.getCode().equals(AuthenticationResponses.USER_INVALID.getResponse())) {
        model.addAttribute("error", getMessage("login.nouser"));
      }

      if (response.getCode().equals(AuthenticationResponses.BAD_CREDENTIALS.getResponse())) {
        model.addAttribute("error", getMessage("login.invalidcredentials"));
      }

      return "auth/login";
    }

    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
    Authentication authentication = authProvider.authenticate(authReq);

    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(authentication);
    HttpSession session = req.getSession(true);
    session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

    AuthSuccessResponse authResponse = authenticationService.onAuthSuccess(req, res, authentication);

    return "redirect:/";
  }

  @PostMapping("/authentication2")
  public String login2(@RequestParam("email") String email,
          @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes, Model model) {

    log.debug("Email: " + email);
    UserEntity user = userService.getUserByEmail(email);

    if (user != null) {
      if (passwordEncoder.matches(password, user.getPassword())) {
        // Password is right
        // Authentication successful
        session.setAttribute("user", user);

        try {
          UserDetails userDetails = webSecurityConfig.userDetailsServiceBean().loadUserByUsername(user.getEmail());

          if (userDetails != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession http = attrs.getRequest().getSession(true);
            http.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "redirect:/";
          }

          return null;
        } catch (UsernameNotFoundException e) {
//                    throw new CustomException(404, "Not found", "User with email " + email + " not found", HttpStatus.NOT_FOUND);
          log.debug("Username not found");
        } catch (Exception e) {
          log.error("Error!", e);
        }
      }
      // Bad credentials
      log.debug("Bad credentials");
      redirectAttributes.addFlashAttribute("badCredentials", true);
      return "redirect:/login";
    }
    // Invalid email
    log.debug("Invalid email");
    redirectAttributes.addFlashAttribute("invalidEmail", true);

    return "redirect:/login";
  }

  @GetMapping("/signup")
  public String prepareSignup(Model model) {

    model.addAttribute("signupModel", new SignupModel());

    return "auth/signup";
  }

  @PostMapping("/signup")
  public String signup(@ModelAttribute("signupModel") @Valid SignupModel signupModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

    if (userService.isUserExistByEmail(signupModel.getEmail())) {
      bindingResult.rejectValue("email", "signup.email.exist");
    }

    if (!signupModel.getPassword().equals(signupModel.getConfirmPassword())) {
      bindingResult.rejectValue("password", "signup.password.nomatch");
      bindingResult.rejectValue("confirmPassword", "signup.password.nomatch");
    }

    if (!passwordValidator.isPasswordValid(signupModel.getPassword())) {
      bindingResult.rejectValue("password", "signup.password.requirements");
      bindingResult.rejectValue("confirmPassword", "signup.password.requirements");
    }

    if (bindingResult.hasErrors()) {
      return "auth/signup";
    }

    UserEntity createdUser = userService.createUser(signupModel);

    return "redirect:/login";
  }
}
