package com.sbc.sas.beans;

import com.sbc.sas.base.*;
import com.sbc.sas.security.*;
import org.slf4j.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

public class UsersBean extends Base
{
  private Logger log = LoggerFactory.getLogger(getClass());

  private SASUser sasUser;

  public SASUser getStoryPrincipal()
  {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return (SASUser) auth.getPrincipal();
  }
}
