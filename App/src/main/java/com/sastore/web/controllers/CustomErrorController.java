package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.enums.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class CustomErrorController extends Base implements ErrorController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            log.debug(statusCode + "");

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                log.debug("Not allowed!");
                model.addAttribute("errorCode", "403");
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                log.debug("Page not found!");
                model.addAttribute("errorCode", "404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                log.debug("Internal server error!");
                model.addAttribute("errorCode", "500");
            }
        }

        if (sc.hasRole(Roles.ADMIN.getRole()) || sc.hasRole(Roles.SALES.getRole())) {
            return "errors/adminError";
        }

        return "errors/error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
