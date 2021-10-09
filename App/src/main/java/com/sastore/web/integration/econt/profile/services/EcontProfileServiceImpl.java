package com.sastore.web.integration.econt.profile.services;

import com.sastore.web.integration.econt.profile.EcontProfilesResponseElement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Service
public class EcontProfileServiceImpl implements EcontProfileService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public List<EcontProfilesResponseElement> getClientProfiles() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
