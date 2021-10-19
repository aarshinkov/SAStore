package com.sastore.web.api;

import com.sastore.web.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public abstract class Api extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private SystemService systemService;
  /**
   * Gets a string with roles such as
   * <br/>
   * <br/>
   * <code>String roles = "USER, ADMIN, CUSTOMER";</code>
   * <br/>
   * <br/>
   * and transforms it in list like this
   * <br/>
   * <br/>
   * <code>[ROLE_USER, ROLE_ADMIN, ROLE_CUSTOMER]</code>
   *
   * @see java.util.List
   * @see org.springframework.security.core.GrantedAuthority
   * @param roles the roles as a string
   * @return a list with the authorities
   */
//    protected List<GrantedAuthority> getRolesFromHeader(String roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        String[] split = roles.split(", ");
//
//        for (String role : split) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//        }
//
//        return authorities;
//    }
  /**
   * Sets and returns the headers, required for the API call
   *
   * @return the populated headers
   */
  protected HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/json");
    headers.set("Content-Type", "application/json");

    return headers;
  }

  /**
   * Gets the Authorization token stored in the session
   *
   * @param request the HTTP request containing the Authorization token in the
   * session
   * @return the authorization token
   */
//  protected String getAuthorizationToken(HttpServletRequest request) {
//    return (String) systemService.getSessionAttribute(request, AppConstants.SESSION_AUTHORIZATION);
//  }
  /**
   * Gets the user public ID stored in the session
   *
   * @param request the HTTP request containing the user public ID in the
   * session
   * @return the stored user public ID
   */
//    protected String getUserPId(HttpServletRequest request) {
//        return (String) systemService.getSessionAttribute(request, AppConstants.SESSION_USER_PUBLIC_ID);
//    }
  //TODO under construction
  private <T> ResponseEntity<T> requestList(HttpMethod method, String url, T type) {
    log.debug("Type: " + type.toString());
    return null;
  }
}
