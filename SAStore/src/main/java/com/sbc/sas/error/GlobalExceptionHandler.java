package com.sbc.sas.error;

import com.sbc.sas.base.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@ControllerAdvice
public class GlobalExceptionHandler extends Base
{

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(Throwable.class)
  public ModelAndView handleException(Exception ex, HttpServletRequest request)
  {
    log.error("Exception occured!", ex);

    String errorMsg;

    if (isDBConnectError(ex))
    {
      log.debug("Database exception!");
      errorMsg = getMessage("error.dbconnect");
    }
    else
    {
      log.debug("Other exception!");
      errorMsg = getMessage("error.system");
    }

    ModelAndView mv = new ModelAndView("error/error");
    mv.addObject("msgError", errorMsg);
    return mv;
  }
}
