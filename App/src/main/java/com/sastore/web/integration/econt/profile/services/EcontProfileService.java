package com.sastore.web.integration.econt.profile.services;

import com.sastore.web.integration.econt.profile.EcontProfilesResponseElement;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public interface EcontProfileService {

  List<EcontProfilesResponseElement> getClientProfiles();
}
