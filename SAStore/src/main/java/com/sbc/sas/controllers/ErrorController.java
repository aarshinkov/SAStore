/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbc.sas.controllers;

import com.sbc.sas.base.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/error")
public class ErrorController extends Base
{
  private Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping(value = "/403")
  public String error403()
  {
    log.debug("Handling error 403!");

    return "error/403";
  }

  @GetMapping(value = "/404")
  public String error404()
  {
    log.debug("Handling error 404!");

    return "error/404";
  }

  @GetMapping(value = "/405")
  public String error405()
  {
    log.debug("Handling error 405!");

    return "error/405";
  }

  @GetMapping(value = "/500")
  public String error500()
  {
    log.debug("Handling error 500!");

    return "error/500";
  }
}
