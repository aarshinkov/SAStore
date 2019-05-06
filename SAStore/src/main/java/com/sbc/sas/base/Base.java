/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbc.sas.base;

import com.sbc.sas.entity.*;
import com.sbc.sas.repository.*;
import com.sbc.sas.security.*;
import java.text.*;
import javax.annotation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.i18n.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

/**
 *
 * @author atanas-PC
 */
public class Base
{
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Resource(name = "messageSource")
  private MessageSource messageSource;

  @Autowired
  private UsersRepository usersRepository;

  private DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

  protected String getMessage(String key)
  {
    return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
  }

  protected String getMessage(String key, Object... params)
  {
    return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
  }

  protected boolean isDBConnectError(Throwable exc)
  {
    Throwable ex = exc;
    while (true)
    {
      if (ex != null)
      {
        log.debug("Ex = " + ex.getClass().getName());
        log.debug("Msg = " + ex.getMessage());

        if (ex.toString().toLowerCase().contains("could not open connection")
                || ex.toString().toLowerCase().contains("cannot get connection")
                || ex.toString().toLowerCase().contains("could not get connection")
                || ex.toString().toLowerCase().contains("connection is closed")
                || ex.toString().toLowerCase().contains("connection pool"))
        {
          return true;
        }
        else
        {
          ex = ex.getCause();
        }
      }
      else
      {
        return false;
      }
    }
  }

  protected SASUser getSASUser()
  {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    try
    {
      return (SASUser) auth.getPrincipal();
    }
    catch (ClassCastException ex)
    {
      log.debug("No logged user has been found!");
      return null;
    }
    catch (Exception e)
    {
      log.error("Error getting SASUser!", e);
      return null;
    }
  }

  protected User getUser()
  {
    return usersRepository.findByUserId(getSASUser().getUserId());
  }
}
